package com.api.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    @Value("${jwt.secret:mySecretKeyThatIsAtLeast32CharactersLongForHS256Algorithm}")
    private String jwtSecret;

    // Default durations
    private static final long DEFAULT_ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000L; // 15 minutes
    private static final long DEFAULT_REFRESH_TOKEN_EXPIRATION = 30L * 24 * 60 * 60 * 1000L; // 30 days

    /**
     * Generate access token (short-lived)
     */
    public String generateAccessToken(String username, Map<String, Object> claims) {
        return createToken(claims, username, DEFAULT_ACCESS_TOKEN_EXPIRATION);
    }

    /**
     * Generate refresh token (long-lived)
     */
    public String generateRefreshToken(String username, Map<String, Object> claims) {
        return createToken(claims, username, DEFAULT_REFRESH_TOKEN_EXPIRATION);
    }

    /**
     * Create JWT token with custom expiration
     */
    private String createToken(Map<String, Object> claims, String subject, long expirationMillis) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Extract username from token
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extract expiration date from token
     */
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    /**
     * Check if token is expired
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validate token
     */
    public Boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername != null && tokenUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * Extract all claims from token
     */
    private Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extract authorities from token claims
     */
    @SuppressWarnings("unchecked")
    public java.util.List<String> extractAuthorities(String token) {
        Object authoritiesClaim = extractAllClaims(token).get("authorities");
        if (authoritiesClaim instanceof java.util.List) {
            return (java.util.List<String>) authoritiesClaim;
        }
        return new java.util.ArrayList<>();
    }
}
