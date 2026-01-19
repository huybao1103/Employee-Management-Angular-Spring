package com.api.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SuppressWarnings("unchecked")
@Configuration
public class AutoMapper extends ModelMapper {
    public <T> T map(Object source) {
        return super.map(source, (Class<T>) source.getClass());
    }

    public <T, F> List<T> mapList(List<F> sourceList) {
        List<T> targetList = new java.util.ArrayList<>();
        for (Object source : sourceList) {
            T target = map(source);
            targetList.add(target);
        }
        return targetList;
    }
}
