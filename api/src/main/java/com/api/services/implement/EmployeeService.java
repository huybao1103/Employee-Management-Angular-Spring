package com.api.services.implement;

import com.api.entities.Employee;
import com.api.models.EmployeeModel;
import com.api.repositories.implement.EmployeeRepository;
import com.api.services.interfaces.IEmployeeService;
import com.api.util.AutoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService implements IEmployeeService {
    private final AutoMapper mapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository, AutoMapper mapper) {
        this.mapper = mapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeModel createEmployee(EmployeeModel employeeModel) {
        Employee emp = mapper.map(employeeModel);
        employeeRepository.createEmployee(emp);
        return employeeModel;
    }

    @Override
    public List<EmployeeModel> getAllEmployees() {
        return mapper.mapList(employeeRepository.getAllEmployees());
    }

    @Override
    public EmployeeModel getEmployeeById(String id) {
        return mapper.map(employeeRepository.getEmployeeById(UUID.fromString(id)));
    }

    @Override
    public EmployeeModel updateEmployee(String id, EmployeeModel employeeModel) {
        EmployeeModel existingEmployeeModel = getEmployeeById(id);
        if (existingEmployeeModel != null) {
            existingEmployeeModel.setName(employeeModel.getName());
            existingEmployeeModel.setDepartment(employeeModel.getDepartment());
            existingEmployeeModel.setSalary(employeeModel.getSalary());
        }
        return existingEmployeeModel;
    }

    @Override
    public void deleteEmployee(String id) {
        employeeRepository.deleteEmployee(UUID.fromString(id));
    }
}
