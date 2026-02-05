package com.api.services.interfaces;

import com.api.entities.User;
import com.api.models.Auth.UserModel;

import java.util.List;

public interface IUserService {
    UserModel createUser(UserModel userModel);
    List<UserModel> getAllUsers();
    User findByUserName(String username);
    User saveUser(User user);
}
