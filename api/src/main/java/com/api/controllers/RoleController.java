package com.api.controllers;


import com.api.models.Role.RoleModel;
import com.api.services.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    // Create a role
    @PostMapping
    public ResponseEntity<RoleModel> createRole(@RequestBody RoleModel roleModel) {
        return ResponseEntity.ok(roleService.createRole(roleModel));
    }

    // Get all roles
    @GetMapping
    public ResponseEntity<List<RoleModel>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    // Get a role by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleModel> getRoleById(@PathVariable String id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    // Update a role
    @PutMapping("/{id}")
    public ResponseEntity<RoleModel> updateRole(@PathVariable String id, @RequestBody RoleModel roleModel) {
        return ResponseEntity.ok(roleService.updateRole(id, roleModel));
    }

    // Delete a role
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/policies")
    public ResponseEntity<List<Map<String, List<Map<String, String>>>>> getAllPolicies() {
        return ResponseEntity.ok(roleService.getAllPolicies());
    }
}
