package com.api.util.mappers.Auth;

import com.api.entities.Role;
import com.api.entities.User;
import com.api.models.User.UpdateUserModel;
import com.api.models.User.UserModel;
import com.api.util.mappers.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface IUpdateUserMapper extends BaseMapper<User, UpdateUserModel> {
    @Mapping(target = "role", source = "role", qualifiedByName = "roleToRoleId")
    UpdateUserModel toDto(User user);

    @Mapping(target = "role", source = "role", qualifiedByName = "roleIdToRole")
    User toEntity(UpdateUserModel userModel);

    @Named("roleIdToRole")
    default Role roleIdToRole(String roleId) {
        if(roleId == null) return null;

        Role role = new Role();
        role.setId(UUID.fromString(roleId));
        return role;
    }

    @Named("roleToRoleId")
    default String roleToRoleId(Role role) {
        if(role == null) return null;
        return role.getId().toString();
    }
}
