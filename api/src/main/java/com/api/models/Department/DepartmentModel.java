package com.api.models.Department;

import java.util.List;

import com.api.entities.Employee;
import com.api.models.Employee.EmployeeModel;

public class DepartmentModel extends DepartmentBasicInfoModel {
    public Employee department_head;
    public List<EmployeeModel> employees;
}
