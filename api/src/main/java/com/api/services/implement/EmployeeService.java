package com.api.services.implement;

import com.api.entities.Employee;
import com.api.models.Employee.EmployeeModel;
import com.api.models.Employee.EmployeeUpdateModel;
import com.api.repositories.interfaces.IEmployeeRepository;
import com.api.services.interfaces.IEmployeeService;
import com.api.util.mappers.Employee.IEmployeeMapper;
import com.api.util.mappers.Employee.IEmployeeUpdateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
class EmployeeService implements IEmployeeService {
    @Autowired
    private IEmployeeMapper employeeMapper;
    @Autowired
    private IEmployeeUpdateMapper employeeUpdateMapper;
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public EmployeeUpdateModel createEmployee(EmployeeUpdateModel employeeModel) {
        employeeModel.id = null;
        Employee emp = employeeUpdateMapper.toEntity(employeeModel);
        employeeRepository.save(emp);
        return employeeModel;
    }

    @Override
    public List<EmployeeModel> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.toDtoList(employees);
    }

    @Override
    public EmployeeUpdateModel getEmployeeById(String id) {
        return employeeUpdateMapper.toDto(employeeRepository.getReferenceById(UUID.fromString(id)));
    }

    @Override
    public EmployeeUpdateModel updateEmployee(String id, EmployeeUpdateModel employeeModel) {
        EmployeeUpdateModel existingEmployeeModel = getEmployeeById(id);
        if (existingEmployeeModel != null) {
            Employee employee = employeeUpdateMapper.toEntity(employeeModel);
            employeeRepository.save(employee);

        }
        return employeeModel;
    }

    @Override
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(UUID.fromString(id));
    }
}
