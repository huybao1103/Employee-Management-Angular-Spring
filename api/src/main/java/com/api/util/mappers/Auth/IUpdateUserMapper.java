package com.api.util.mappers.Auth;

import com.api.entities.User;
import com.api.models.User.UpdateUserModel;
import com.api.util.mappers.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUpdateUserMapper extends BaseMapper<User, UpdateUserModel> {
}
