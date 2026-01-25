package com.api.util.mappers;

import com.api.entities.Employee;
import com.api.models.EmployeeModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEmployeeMapper extends BaseMapper<Employee, EmployeeModel> {
}
