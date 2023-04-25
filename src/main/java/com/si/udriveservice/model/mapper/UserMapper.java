package com.si.udriveservice.model.mapper;

import com.si.udriveservice.model.dto.UserDTO;
import com.si.udriveservice.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserDTO> {
}
