package com.api.services.interfaces;

import com.api.entities.User;
import com.api.models.User.UpdateUserModel;
import com.api.models.User.UserModel;

import java.util.List;

public interface IUserService {
    UpdateUserModel createUser(UpdateUserModel userModel);
    List<UserModel> getAllUsers();
    User findByUserName(String username);
    User saveUser(User user);

    UpdateUserModel getUserById(String id);

    void deleteUser(String id);

    UpdateUserModel updateUser(String id, UpdateUserModel updateUserModel);
}
