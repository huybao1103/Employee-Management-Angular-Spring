package com.api.repositories.implement;

import com.api.entities.Employee;
import com.api.repositories.interfaces.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public abstract class EmployeeRepository implements IEmployeeRepository {
}
