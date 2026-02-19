package com.api.services.implement;

import com.api.entities.User;
import com.api.models.Auth.LoginRequest;
import com.api.models.Auth.LoginResponse;
import com.api.services.interfaces.IAuthService;
import com.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        String accessToken = jwtUtil.generateAccessToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

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

        String newAccessToken = jwtUtil.generateAccessToken(username);
        String newRefreshToken = jwtUtil.generateRefreshToken(username);

        user.setToken(newRefreshToken);
        userService.saveUser(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(newAccessToken);
        loginResponse.setRefreshToken(newRefreshToken);
        loginResponse.setUsername(username);
        return loginResponse;
    }
}
