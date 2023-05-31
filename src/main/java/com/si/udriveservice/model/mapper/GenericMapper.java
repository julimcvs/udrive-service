package com.si.udriveservice.model.mapper;

import java.io.Serializable;

public interface GenericMapper<T extends Serializable, D extends Serializable> {

    D toDto(T entity);

    T toEntity(D dto);
}