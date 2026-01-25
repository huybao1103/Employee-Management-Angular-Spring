package com.api.repositories.interfaces;

import com.api.entities.Employee;
import com.api.repositories.IGeneralRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IEmployeeRepository extends IGeneralRepository<Employee> {
}
