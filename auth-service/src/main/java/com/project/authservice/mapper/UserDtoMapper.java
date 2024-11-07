package com.project.authservice.mapper;

import com.project.authservice.model.dto.UserDto;
import com.project.authservice.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
