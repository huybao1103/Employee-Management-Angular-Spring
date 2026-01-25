package com.api.repositories;

import com.api.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IGeneralRepository<T extends BaseEntity> extends JpaRepository<T, UUID>  {
}
