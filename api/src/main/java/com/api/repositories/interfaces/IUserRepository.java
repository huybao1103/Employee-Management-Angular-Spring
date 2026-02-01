package com.api.repositories.interfaces;

import com.api.entities.User;
import com.api.repositories.IGeneralRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends IGeneralRepository<User> {
    User findByUserName(String user_name);
}
