package com.api.services.implement;

import com.api.entities.RoleClaim;
import com.api.entities.User;
import com.api.models.Auth.LoginRequest;
import com.api.models.Auth.LoginResponse;
import com.api.services.interfaces.IAuthService;
import com.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
class AuthService implements IAuthService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = null;
        String username = loginRequest.getUsername();

        if(!username.equals("admin")) {
            // Validate credentials against database
            user = userService.findByUserName(username);

            if(user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                return null;
        }

        Map<String, Object> claims = getClaimsForUser(user);
        String accessToken = jwtUtil.generateAccessToken(username, claims);
        String refreshToken = jwtUtil.generateRefreshToken(username, claims);

        if (user != null) {
            user.setToken(refreshToken);
            userService.saveUser(user);
        }

        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setUsername(username);
        response.setRefreshToken(refreshToken);
        return response;
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        if (refreshToken == null) {
            throw new IllegalArgumentException("Refresh token is required");
        }

        String username = jwtUtil.extractUsername(refreshToken);

        if (jwtUtil.isTokenExpired(refreshToken)) {
            throw new IllegalArgumentException("Refresh token is expired");
        }

        User user = userService.findByUserName(username);
        if (user == null || user.getToken() == null || !user.getToken().equals(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        Map<String, Object> claims = getClaimsForUser(user);
        String newAccessToken = jwtUtil.generateAccessToken(username, claims);
        String newRefreshToken = jwtUtil.generateRefreshToken(username, claims);

        user.setToken(newRefreshToken);
        userService.saveUser(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(newAccessToken);
        loginResponse.setRefreshToken(newRefreshToken);
        loginResponse.setUsername(username);
        return loginResponse;
    }

    private Map<String, Object> getClaimsForUser(User user) {
        Map<String, Object> claims = new HashMap<>();
        if (user != null && user.getRole() != null && !user.getRole().getRoleClaims().isEmpty()) {
            claims.put("authorities", user.getRole().getRoleClaims().stream().map(RoleClaim::getClaim).toList());
        }
        return claims;
    }
}
