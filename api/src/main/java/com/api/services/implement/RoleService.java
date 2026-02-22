package com.api.services.implement;

import com.api.entities.Role;
import com.api.entities.RoleClaim;
import com.api.models.Role.RoleModel;
import com.api.repositories.interfaces.IRoleRepository;
import com.api.services.interfaces.IRoleService;
import com.api.util.mappers.Role.IRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.api.util.Auth.Policies.DepartmentPolicies.ALL_DEPARTMENT_POLICIES_NAME;
import static com.api.util.Auth.Policies.Employee.EmployeePolicies.ALL_EMP_POLICIES_NAME;
import static com.api.util.Auth.Policies.UserPolicies.ALL_USER_POLICIES_NAME;

@Service
class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IRoleMapper roleMapper;

    @Override
    public RoleModel createRole(RoleModel roleModel) {
        roleModel.id = null;

        Role role = roleMapper.toEntity(roleModel);
//        if (role.getRoleClaims() != null && !role.getRoleClaims().isEmpty()) {
//            for (RoleClaim rc : role.getRoleClaims()) {
//                rc.setRole(role);
//            }
//        }
        roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    @Override
    public List<RoleModel> getAllRoles() {
        return roleMapper.toDtoList(roleRepository.findAll());
    }

    @Override
    public RoleModel getRoleById(String id) {
        return roleMapper.toDto(roleRepository.getReferenceById(UUID.fromString(id)));
    }

    @Override
    public RoleModel updateRole(String id, RoleModel roleModel) {
        RoleModel existingRoleModel = getRoleById(id);
        if (existingRoleModel != null) {
            Role role = roleMapper.toEntity(roleModel);
            role.setId(UUID.fromString(id));
            if (role.getRoleClaims() != null && !role.getRoleClaims().isEmpty()) {
                for (RoleClaim rc : role.getRoleClaims()) {
                    rc.setRole(role);
                }
            }
            roleRepository.save(role);
        }
        return roleModel;
    }

    @Override
    public void deleteRole(String id) {
        roleRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public List<Map<String, List<Map<String, String>>>> getAllPolicies() {
        return List.of(
                Map.of("Employee Policies", ALL_EMP_POLICIES_NAME),
                Map.of("Department Policies", ALL_DEPARTMENT_POLICIES_NAME),
                Map.of("User Policies", ALL_USER_POLICIES_NAME)
        );
    }
}
