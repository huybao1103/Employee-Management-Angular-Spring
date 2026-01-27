package com.api.util.mappers.Employee;

import com.api.entities.Department;
import com.api.entities.Employee;
import com.api.models.Department.DepartmentModel;
import com.api.models.Employee.EmployeeModel;
import com.api.util.mappers.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IEmployeeMapper extends BaseMapper<Employee, EmployeeModel> {
    @Mapping(target = "employees", ignore = true)
    DepartmentModel toDepartmentDto(Department entity);
}
