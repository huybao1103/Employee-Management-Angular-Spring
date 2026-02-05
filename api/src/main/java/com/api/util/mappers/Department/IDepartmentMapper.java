package com.api.util.mappers.Department;

import org.mapstruct.Mapper;

import com.api.entities.Department;
import com.api.models.Department.DepartmentModel;
import com.api.util.mappers.BaseMapper;

@Mapper(componentModel = "spring")
public interface IDepartmentMapper extends BaseMapper<Department, DepartmentModel> {
}
