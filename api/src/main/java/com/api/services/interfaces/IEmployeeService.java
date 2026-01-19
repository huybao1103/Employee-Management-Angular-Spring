package com.api.services.interfaces;

import com.api.models.EmployeeModel;

import java.util.List;

public interface IEmployeeService {
    EmployeeModel createEmployee(EmployeeModel employeeModel);
    List<EmployeeModel> getAllEmployees();
    EmployeeModel getEmployeeById(String id);
    EmployeeModel updateEmployee(String id, EmployeeModel employeeModel);
    void deleteEmployee(String id);
}
