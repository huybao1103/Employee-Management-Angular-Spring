package com.api.services.implement;

import com.api.entities.Employee;
import com.api.models.EmployeeModel;
import com.api.repositories.implement.EmployeeRepository;
import com.api.repositories.interfaces.IEmployeeRepository;
import com.api.services.interfaces.IEmployeeService;
import com.api.util.mappers.IEmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private IEmployeeMapper employeeMapper;
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public EmployeeModel createEmployee(EmployeeModel employeeModel) {
        Employee emp = employeeMapper.toEntity(employeeModel);
        employeeRepository.save(emp);
        return employeeModel;
    }

    @Override
    public List<EmployeeModel> getAllEmployees() {
        return employeeMapper.toDtoList(employeeRepository.findAll());
    }

    @Override
    public EmployeeModel getEmployeeById(String id) {
        return employeeMapper.toDto(employeeRepository.getReferenceById(UUID.fromString(id)));
    }

    @Override
    public EmployeeModel updateEmployee(String id, EmployeeModel employeeModel) {
        EmployeeModel existingEmployeeModel = getEmployeeById(id);
//        if (existingEmployeeModel != null) {
//            existingEmployeeModel.setName(employeeModel.getName());
//            existingEmployeeModel.setDepartment(employeeModel.getDepartment());
//            existingEmployeeModel.setSalary(employeeModel.getSalary());
//        }
        return existingEmployeeModel;
    }

    @Override
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(UUID.fromString(id));
    }
}
