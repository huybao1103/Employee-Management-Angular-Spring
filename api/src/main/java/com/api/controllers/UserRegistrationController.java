package com.api.controllers;

import com.api.models.Auth.UserModel;
import com.api.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserRegistrationController {
    
    @Autowired
    private IUserService userService;
    
    /**
     * Register a new user
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel userModel) {
        try {
            if (userModel.userName == null || userModel.userName.isBlank()) {
                return ResponseEntity.badRequest().body("Username is required");
            }
            if (userModel.password == null || userModel.password.isBlank()) {
                return ResponseEntity.badRequest().body("Password is required");
            }
            
            UserModel createdUser = userService.createUser(userModel);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("username", createdUser.userName);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }
}
