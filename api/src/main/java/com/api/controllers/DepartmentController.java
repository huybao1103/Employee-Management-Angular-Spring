package com.api.controllers;

import com.api.models.Department.DepartmentBasicInfoModel;
import com.api.models.Department.DepartmentModel;
import com.api.models.OptionsModel;
import com.api.services.interfaces.IDepartmentService;
import com.api.util.Auth.Policies.DepartmentPolicies;
import com.api.util.Auth.Policies.PolicySecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    // Create a department
    @PolicySecurityService.HasAllClaims(claims = { DepartmentPolicies.VIEW })
    @PostMapping
    public ResponseEntity<DepartmentBasicInfoModel> createDepartment(@RequestBody DepartmentBasicInfoModel departmentModel) {
        return ResponseEntity.ok(departmentService.createDepartment(departmentModel));
    }

    // Get all departments
    @PolicySecurityService.HasAllClaims(claims = { DepartmentPolicies.VIEW })
    @GetMapping
    public ResponseEntity<List<DepartmentModel>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    // Get a department by ID
    @PolicySecurityService.HasAllClaims(claims = { DepartmentPolicies.VIEW })
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentModel> getDepartmentById(@PathVariable String id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    // Update a department
    @PolicySecurityService.HasAllClaims(claims = { DepartmentPolicies.VIEW, DepartmentPolicies.EDIT })
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentModel> updateDepartment(@PathVariable String id, @RequestBody DepartmentModel departmentModel) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentModel));
    }

    // Delete a department
    @PolicySecurityService.HasAllClaims(claims = { DepartmentPolicies.VIEW, DepartmentPolicies.EDIT, DepartmentPolicies.REMOVE })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    // Get all departments as options list
    @GetMapping("/options")
    public ResponseEntity<List<OptionsModel>> getAllDepartmentOptions() {
        return ResponseEntity.ok(departmentService.getDepartmentOptions());
    }
}
