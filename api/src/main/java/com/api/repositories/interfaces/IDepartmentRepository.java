package com.api.repositories.interfaces;

import com.api.entities.Department;
import com.api.repositories.IGeneralRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepartmentRepository extends IGeneralRepository<Department> {
}
