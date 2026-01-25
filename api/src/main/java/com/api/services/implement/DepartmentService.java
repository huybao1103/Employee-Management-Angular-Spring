package com.api.services.implement;

import com.api.entities.Department;
import com.api.entities.Employee;
import com.api.models.DepartmentBasicInfoModel;
import com.api.models.DepartmentModel;
import com.api.repositories.interfaces.IDepartmentRepository;
import com.api.services.interfaces.IDepartmentService;
import com.api.util.mappers.IDepartmentBasicInfoMapper;
import com.api.util.mappers.IDepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService implements IDepartmentService {
    @Autowired
    private IDepartmentMapper departmentMapper;
    @Autowired
    private IDepartmentBasicInfoMapper departmentBasicInfoMapper;
    @Autowired
    private IDepartmentRepository departmentRepository;

    @Override
    public DepartmentBasicInfoModel createDepartment(DepartmentBasicInfoModel departmentModel) {
        Department department = departmentBasicInfoMapper.toEntity(departmentModel);
        departmentRepository.save(department);
        return null;
    }

    @Override
    public List<DepartmentModel> getAllDepartments() {
        return departmentMapper.toDtoList(departmentRepository.findAll());
    }

    @Override
    public DepartmentModel getDepartmentById(String id) {
        return departmentMapper.toDto(departmentRepository.getReferenceById(UUID.fromString(id)));
    }

    @Override
    public DepartmentModel updateDepartment(String id, DepartmentModel departmentModel) {
        return null;
    }

    @Override
    public void deleteDepartment(String id) {
        departmentRepository.deleteById(UUID.fromString(id));
    }
}
