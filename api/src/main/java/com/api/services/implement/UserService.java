package com.api.services.implement;

import com.api.entities.Department;
import com.api.entities.User;
import com.api.models.Department.DepartmentModel;
import com.api.models.User.UpdateUserModel;
import com.api.models.User.UserModel;
import com.api.repositories.interfaces.IUserRepository;
import com.api.services.interfaces.IUserService;
import com.api.util.mappers.Auth.IUpdateUserMapper;
import com.api.util.mappers.Auth.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private IUpdateUserMapper updateUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UpdateUserModel createUser(UpdateUserModel userModel) {
        userModel.id = null;
        User user = updateUserMapper.toEntity(userModel);
        user.setPassword(passwordEncoder.encode(userModel.password));
        userRepository.save(user);
        return userModel;
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }
    
    @Override
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UpdateUserModel getUserById(String id) {
        return updateUserMapper.toDto(userRepository.getReferenceById(UUID.fromString(id)));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public UpdateUserModel updateUser(String id, UpdateUserModel updateUserModel) {
        UpdateUserModel existingUser = getUserById(id);
        if(existingUser != null){
            User user = updateUserMapper.toEntity(updateUserModel);
            user.setPassword(existingUser.password);
            userRepository.save(user);
        }
        return null;
    }
}
