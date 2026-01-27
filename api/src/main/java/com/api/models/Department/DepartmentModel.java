package com.api.models.Department;

import com.api.entities.Employee;
import com.api.models.BaseModel;
import com.api.models.Employee.EmployeeModel;

import java.util.List;

public class DepartmentModel extends DepartmentBasicInfoModel {
    public Employee department_head;
    public List<EmployeeModel> employees;
}
