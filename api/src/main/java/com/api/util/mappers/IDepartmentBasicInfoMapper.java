package com.api.util.mappers;

import com.api.entities.Department;
import com.api.models.DepartmentBasicInfoModel;
import com.api.models.DepartmentModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDepartmentBasicInfoMapper extends BaseMapper<Department, DepartmentBasicInfoModel>{
}
