package com.api.util.mappers;

import com.api.entities.BaseEntity;
import com.api.models.BaseModel;

import java.util.List;

public interface BaseMapper<E extends BaseEntity, D extends BaseModel> {

    D toDto(E entity);

    E toEntity(D dto);

    default List<D> toDtoList(List<E> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    default List<E> toEntityList(List<D> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }
}

