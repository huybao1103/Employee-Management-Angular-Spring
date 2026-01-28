package com.api.services.interfaces;

import com.api.entities.User;
import com.api.models.Auth.UserModel;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    UserModel createUser(UserModel userModel);
    List<UserModel> getAllUsers();
    Optional<User> findByUsername(String username);
}
