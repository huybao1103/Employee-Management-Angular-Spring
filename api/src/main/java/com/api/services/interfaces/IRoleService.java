package com.api.services.interfaces;

import com.api.models.Role.RoleModel;

import java.util.List;
import java.util.Map;

public interface IRoleService {
    RoleModel createRole(RoleModel roleModel);
    List<RoleModel> getAllRoles();
    RoleModel getRoleById(String id);
    RoleModel updateRole(String id, RoleModel roleModel);
    void deleteRole(String id);

    List<Map<String, List<Map<String, String>>>> getAllPolicies();
}
