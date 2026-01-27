package com.api.models.Employee;

import com.api.models.BaseModel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmployeeBasicModel extends BaseModel {
    public String id;

    @NotNull
    @Size(min = 1, max = 200)
    public String name;

    @Email
    public String email;

    @NotNull
    @DecimalMin("0")
    public double salary;

    public String createdAt;
}
