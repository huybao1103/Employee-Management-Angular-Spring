package com.api.util.mappers.Department;

import com.api.entities.Department;
import com.api.models.Department.DepartmentModel;
import com.api.util.mappers.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDepartmentMapper extends BaseMapper<Department, DepartmentModel> {
}

