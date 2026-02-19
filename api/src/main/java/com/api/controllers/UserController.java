package com.api.controllers;

import com.api.models.Department.DepartmentModel;
import com.api.models.OptionsModel;
import com.api.models.User.UpdateUserModel;
import com.api.models.User.UserModel;
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
    public ResponseEntity<UpdateUserModel> createUser(@RequestBody UpdateUserModel userModel) {
        return ResponseEntity.ok(userService.createUser(userModel));
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UpdateUserModel> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Update a user
    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserModel> updateUser(@PathVariable String id, @RequestBody UpdateUserModel updateUserModel) {
        return ResponseEntity.ok(userService.updateUser(id, updateUserModel));
    }
}
