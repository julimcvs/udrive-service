package com.si.udriveservice.model.mapper;

import com.si.udriveservice.model.dto.DriverDTO;
import com.si.udriveservice.model.entity.Driver;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper extends GenericMapper<Driver, DriverDTO> {
}
