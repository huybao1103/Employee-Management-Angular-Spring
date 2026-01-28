package com.api.controllers;

import com.api.models.Auth.UserModel;
import com.api.models.Employee.EmployeeModel;
import com.api.models.Employee.EmployeeUpdateModel;
import com.api.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    // Create a user
    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(userService.createUser(userModel));
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
