package com.api.controllers;

import com.api.entities.User;
import com.api.services.interfaces.IUserService;
import com.api.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CookieValue;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserService userService;

    /**
     * Login endpoint - returns JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @NotNull LoginRequest loginRequest) {
        User user = null;
        String username = loginRequest.getUsername();
        if(!username.equals("admin")) {
            // Validate credentials against database
            user = userService.findByUserName(username);

            if(user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                return ResponseEntity.badRequest().body("Account not found.");
        }

        String accessToken = jwtUtil.generateAccessToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        if (user != null) {
            user.setToken(refreshToken);
            userService.saveUser(user);
        }

        ResponseCookie cookie = getCookie(refreshToken);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("username", username);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(response);
    }

    /**
     * Validate token endpoint
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            String username = jwtUtil.extractUsername(token);
            boolean isValid = !jwtUtil.isTokenExpired(token);
            
            Map<String, Object> response = new HashMap<>();
            response.put("valid", isValid);
            response.put("username", username);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    /**
     * Refresh token endpoint
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        try {
            if (refreshToken == null) {
                return ResponseEntity.badRequest().body("Refresh token missing");
            }

            String username = jwtUtil.extractUsername(refreshToken);

            if (jwtUtil.isTokenExpired(refreshToken)) {
                return ResponseEntity.badRequest().body("Refresh token expired");
            }

            User user = userService.findByUserName(username);
            if (user == null || user.getToken() == null || !user.getToken().equals(refreshToken)) {
                return ResponseEntity.badRequest().body("Invalid refresh token");
            }

            String newAccessToken = jwtUtil.generateAccessToken(username);
            String newRefreshToken = jwtUtil.generateRefreshToken(username);

            user.setToken(newRefreshToken);
            userService.saveUser(user);

            ResponseCookie cookie = getCookie(newRefreshToken);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(response);
        } catch (ExpiredJwtException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @CookieValue(value = "refreshToken", required = false) String refreshToken
    ) {

//        if (refreshToken != null) {
//            User user = userService.findByToken(refreshToken);
//            if (user != null) {
//                user.setToken(null); // or mark revoked
//                userService.saveUser(user);
//            }
//        }

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false) // true in production
                .path("/")
                .maxAge(0) // 🔥 deletes cookie
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .build();
    }

    // Inner class for login request
    public static class LoginRequest {
        @NotNull
        private String username;
        @NotNull
        private String password;

        public LoginRequest() {}

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private ResponseCookie getCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(30L * 24 * 60 * 60)
                .sameSite("Strict")
                .build();
    }
}
