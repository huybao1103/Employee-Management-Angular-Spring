package com.api.services.interfaces;

import com.api.models.Employee.EmployeeModel;
import com.api.models.Employee.EmployeeUpdateModel;

import java.util.List;

public interface IEmployeeService {
    EmployeeUpdateModel createEmployee(EmployeeUpdateModel employeeModel);
    List<EmployeeModel> getAllEmployees();
    EmployeeUpdateModel getEmployeeById(String id);
    EmployeeUpdateModel updateEmployee(String id, EmployeeUpdateModel employeeModel);
    void deleteEmployee(String id);
}
