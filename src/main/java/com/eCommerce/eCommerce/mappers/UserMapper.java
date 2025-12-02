package com.eCommerce.eCommerce.mappers;

import com.eCommerce.eCommerce.dtos.RegisterUserRequest;
import com.eCommerce.eCommerce.dtos.UpdateUserRequest;
import com.eCommerce.eCommerce.dtos.UserDto;
import com.eCommerce.eCommerce.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest  request);
    void update(UpdateUserRequest request,@MappingTarget User user );
}
