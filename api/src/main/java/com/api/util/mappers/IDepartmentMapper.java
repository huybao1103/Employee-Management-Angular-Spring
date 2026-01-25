package com.api.util.mappers;

import com.api.entities.Department;
import com.api.models.DepartmentModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDepartmentMapper extends BaseMapper<Department, DepartmentModel>{
}

