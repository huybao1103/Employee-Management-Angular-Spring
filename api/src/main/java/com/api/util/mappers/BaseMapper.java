package com.api.util.mappers;

import com.api.entities.BaseEntity;
import com.api.models.BaseModel;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

public interface BaseMapper<E extends BaseEntity, D extends BaseModel> {

    D toDto(E entity);

    E toEntity(D dto);

    default String map(UUID value) {
        return value != null ? value.toString() : null;
    }

    default UUID map(String value) {
        return value != null ? UUID.fromString(value) : null;
    }
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

