package com.api.util.mappers.Department;

import com.api.entities.Department;
import com.api.models.Department.DepartmentBasicInfoModel;
import com.api.util.mappers.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDepartmentBasicInfoMapper extends BaseMapper<Department, DepartmentBasicInfoModel> {
}
