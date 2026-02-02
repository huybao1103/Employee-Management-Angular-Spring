package com.api.util.mappers.Auth;

import com.api.entities.User;
import com.api.models.Auth.UserModel;
import com.api.util.mappers.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserMapper extends BaseMapper<User, UserModel> {
    @Mapping(target = "userName", source = "userName")
    User toEntity(UserModel userModel);
}
