package com.api.controllers;

import com.api.models.Auth.LoginRequest;
import com.api.models.Auth.LoginResponse;
import com.api.services.interfaces.IAuthService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private IAuthService authService;

    /**
     * Login endpoint - returns JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @NotNull LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        if (loginResponse == null) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        ResponseCookie cookie = getCookie(loginResponse.getRefreshToken());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(loginResponse);
    }

    /**
     * Refresh token endpoint
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        try {
            LoginResponse loginResponse = authService.refreshToken(refreshToken);
            String newRefreshToken = loginResponse.getRefreshToken();

            ResponseCookie cookie = getCookie(newRefreshToken);

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(loginResponse);
        } catch (ExpiredJwtException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .build();
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
