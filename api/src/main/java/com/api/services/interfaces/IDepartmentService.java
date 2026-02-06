package com.api.services.interfaces;

import com.api.models.Department.DepartmentBasicInfoModel;
import com.api.models.Department.DepartmentModel;
import com.api.models.OptionsModel;

import java.util.List;

public interface IDepartmentService {
    DepartmentBasicInfoModel createDepartment(DepartmentBasicInfoModel departmentModel);
    List<DepartmentModel> getAllDepartments();
    DepartmentModel getDepartmentById(String id);
    DepartmentModel updateDepartment(String id, DepartmentModel departmentModel);
    void deleteDepartment(String id);
    List<OptionsModel> getDepartmentOptions();
}
