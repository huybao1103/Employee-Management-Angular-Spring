package com.api.models.Auth;

import com.api.models.BaseModel;
import jakarta.persistence.Column;

public class UserModel extends BaseModel {
    public String user_name;

    public String password;

    public String role;
}
