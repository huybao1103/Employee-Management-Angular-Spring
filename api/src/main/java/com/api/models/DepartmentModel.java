package com.api.models;

import com.api.entities.Employee;

import java.util.List;

public class DepartmentModel extends BaseModel {
    public String name;
    public String address;
    public String email;
    public Employee department_head;
    public List<EmployeeModel> employees;
}
