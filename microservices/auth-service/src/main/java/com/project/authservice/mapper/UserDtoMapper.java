package com.project.authservice.mapper;

import com.project.authservice.model.dto.UserDto;
import com.project.authservice.model.entity.User;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between {@link UserDto} and {@link User}.
 * This interface uses MapStruct to generate the necessary implementation
 * for mapping the entity {@link User} to the DTO {@link UserDto} and vice versa.
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    /**
     * Converts a {@link User} entity to a {@link UserDto} DTO.
     *
     * @param user the {@link User} entity to be converted
     * @return the {@link UserDto} DTO corresponding to the given entity
     */
    UserDto toDto(User user);

    /**
     * Converts a {@link UserDto} DTO to a {@link User} entity.
     *
     * @param userDto the {@link UserDto} DTO to be converted
     * @return the {@link User} entity corresponding to the given DTO
     */
    User toEntity(UserDto userDto);
}
