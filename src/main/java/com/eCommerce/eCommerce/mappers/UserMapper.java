package com.eCommerce.eCommerce.mappers;

import com.eCommerce.eCommerce.dtos.UserDto;
import com.eCommerce.eCommerce.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
