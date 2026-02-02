package com.api.models.Auth;

import com.api.models.BaseModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserModel extends BaseModel {
    public String id;
    @NotNull
    public String userName;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 64, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*]).+$",
        message = "Password must contain uppercase, lowercase, number and special character")
    public String password;
    @NotNull
    public String role;
}
