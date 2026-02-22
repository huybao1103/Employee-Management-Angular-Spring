package com.api.models.Role;

import com.api.models.BaseModel;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public class RoleModel extends BaseModel {
    public String id;
    @NotNull
    public String name;
    public ArrayList<String> claims = new ArrayList<>();
}
