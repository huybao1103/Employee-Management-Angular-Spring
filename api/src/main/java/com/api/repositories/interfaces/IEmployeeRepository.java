package com.api.repositories.interfaces;

import org.springframework.stereotype.Repository;

import com.api.entities.Employee;
import com.api.repositories.IGeneralRepository;

@Repository
public interface IEmployeeRepository extends IGeneralRepository<Employee> {
}
