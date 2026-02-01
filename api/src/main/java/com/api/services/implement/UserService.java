package com.api.services.implement;

import com.api.entities.User;
import com.api.models.Auth.UserModel;
import com.api.repositories.interfaces.IUserRepository;
import com.api.services.interfaces.IUserService;
import com.api.util.mappers.Auth.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserModel createUser(UserModel userModel) {
        User user = userMapper.toEntity(userModel);
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
}
