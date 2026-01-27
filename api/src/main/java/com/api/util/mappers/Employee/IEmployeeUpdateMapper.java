package com.api.util.mappers.Employee;

import com.api.entities.Department;
import com.api.entities.Employee;
import com.api.models.Employee.EmployeeUpdateModel;
import com.api.util.mappers.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface IEmployeeUpdateMapper extends BaseMapper<Employee, EmployeeUpdateModel> {
    @Named("departmentIdToDepartment")
    default Department departmentIdToDepartment(String departmentId) {
        if(departmentId == null) return null;

        Department department = new Department();
        department.setId(UUID.fromString(departmentId));
        return department;
    }

    @Override
    @Mapping(
        target = "department",
        source = "department_id",
        qualifiedByName = "departmentIdToDepartment"
    )
    Employee toEntity(EmployeeUpdateModel value);
    EmployeeUpdateModel toDto(Employee value);
}
