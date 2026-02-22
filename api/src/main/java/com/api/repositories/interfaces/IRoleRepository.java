package com.api.repositories.interfaces;

import com.api.entities.Role;
import com.api.repositories.IGeneralRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends IGeneralRepository<Role> {
}
