package com.api.models.User;

import com.api.models.BaseModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserModel extends BaseModel {
    public String id;
    @NotNull
    public String userName;
    @NotNull
    public String role;
}
