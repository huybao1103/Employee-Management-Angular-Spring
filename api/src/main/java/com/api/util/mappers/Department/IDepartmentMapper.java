package com.api.util.mappers.Department;

import com.api.entities.Department;
import com.api.entities.Employee;
import com.api.models.Department.DepartmentModel;
import com.api.models.Employee.EmployeeModel;
import com.api.util.mappers.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IDepartmentMapper extends BaseMapper<Department, DepartmentModel> {
//    @Mapping(target = "employee", ignore = true)
//    EmployeeModel toEmployeeDto(Employee entity);
}

