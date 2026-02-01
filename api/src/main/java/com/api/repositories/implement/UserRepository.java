package com.api.repositories.implement;

import com.api.entities.User;
import com.api.repositories.interfaces.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public abstract class UserRepository implements IUserRepository {
    @Override
    public User findByUserName(String user_name) {

        return null;
    }
}
