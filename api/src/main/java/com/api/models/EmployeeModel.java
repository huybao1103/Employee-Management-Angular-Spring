package com.api.models;

public class EmployeeModel extends BaseModel {
    public String id;
    public String name;
    public String email;
    public DepartmentModel department;
    public double salary;
    public String createdAt;
}
