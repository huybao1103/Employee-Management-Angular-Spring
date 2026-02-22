package com.api.controllers;

import com.api.models.Employee.EmployeeUpdateModel;
import com.api.util.Auth.Policies.Employee.EmployeePolicies;
import com.api.util.Auth.Policies.Employee.EmployeePoliciesEnum;
import com.api.util.Auth.Policies.PolicySecurityService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.models.Employee.EmployeeModel;
import com.api.services.interfaces.IEmployeeService;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    // Create an employee
    @HasAllClaims(claims = { EmployeePolicies.VIEW, EmployeePolicies.EDIT })
    @PostMapping
    public ResponseEntity<EmployeeUpdateModel> createEmployee(@RequestBody EmployeeUpdateModel employeeModel) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeModel));
    }

    // Get all employees
    @HasAllClaims(claims = { EmployeePolicies.VIEW })
    @GetMapping
    public ResponseEntity<List<EmployeeModel>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // Get an employee by ID
    @HasAllClaims(claims = { EmployeePolicies.VIEW })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeUpdateModel> getEmployeeById(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // Update an employee
    @HasAllClaims(claims = { EmployeePolicies.VIEW, EmployeePolicies.EDIT })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeUpdateModel> updateEmployee(@PathVariable String id, @RequestBody EmployeeUpdateModel employeeModel) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeModel));
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    @HasAllClaims(claims = { EmployeePolicies.VIEW, EmployeePolicies.EDIT, EmployeePolicies.REMOVE })
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
