package com.api.controllers;

import com.api.models.DepartmentBasicInfoModel;
import com.api.models.DepartmentModel;
import com.api.services.interfaces.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    // Create an employee
    @PostMapping
    public ResponseEntity<DepartmentBasicInfoModel> createDepartment(@RequestBody DepartmentBasicInfoModel departmentModel) {
        return ResponseEntity.ok(departmentService.createDepartment(departmentModel));
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<DepartmentModel>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    // Get an employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentModel> getDepartmentById(@PathVariable String id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    // Update an employee
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentModel> updateDepartment(@PathVariable String id, @RequestBody DepartmentModel departmentModel) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentModel));
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
