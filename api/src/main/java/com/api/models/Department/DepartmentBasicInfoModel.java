package com.api.models.Department;

import com.api.models.BaseModel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class DepartmentBasicInfoModel extends BaseModel {
    public String id;
    @NotNull
    public String name;
    @NotNull
    public String address;
    @Email
    public String email;
}
