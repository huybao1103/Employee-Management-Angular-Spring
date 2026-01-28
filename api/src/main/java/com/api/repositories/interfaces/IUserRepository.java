package com.api.repositories.interfaces;

import com.api.entities.User;
import com.api.repositories.IGeneralRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends IGeneralRepository<User> {
    Optional<User> findByUsername(String username);
}
