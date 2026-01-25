package com.api.services.interfaces;

import com.api.models.DepartmentBasicInfoModel;
import com.api.models.DepartmentModel;

import java.util.List;

public interface IDepartmentService {
    DepartmentBasicInfoModel createDepartment(DepartmentBasicInfoModel departmentModel);
    List<DepartmentModel> getAllDepartments();
    DepartmentModel getDepartmentById(String id);
    DepartmentModel updateDepartment(String id, DepartmentModel departmentModel);
    void deleteDepartment(String id);
}
