package com.api.util.mappers.Auth;

import com.api.entities.Role;
import com.api.entities.User;
import com.api.models.User.UserModel;
import com.api.util.mappers.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface IUserMapper extends BaseMapper<User, UserModel> {
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "role", ignore = true)
    User toEntity(UserModel userModel);

    @Mapping(target = "role", source = "role", qualifiedByName = "roleToRoleName")
    UserModel toDto(User user);

    @Named("roleToRoleName")
    default String roleToRoleName(Role role) {
        if(role == null) return null;
        return role.getName();
    }
}
