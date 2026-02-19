package com.api.repositories.implement;

import org.springframework.stereotype.Repository;

import com.api.entities.User;
import com.api.repositories.interfaces.IUserRepository;

@Repository
abstract class UserRepository implements IUserRepository {
    @Override
    public User findByUserName(String user_name) {

        return null;
    }
}
