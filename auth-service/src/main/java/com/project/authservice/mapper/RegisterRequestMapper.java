package com.project.authservice.mapper;

import com.project.authservice.model.dto.RegisterRequest;
import com.project.authservice.model.entity.User;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between {@link RegisterRequest} and {@link User}.
 * This interface uses MapStruct to generate the necessary implementation
 * for mapping the DTO {@link RegisterRequest} to the entity {@link User}.
 */
@Mapper(componentModel = "spring")
public interface RegisterRequestMapper {

    /**
     * Converts a {@link RegisterRequest} DTO to a {@link User} entity.
     *
     * @param registerRequest the {@link RegisterRequest} DTO to be converted
     * @return the {@link User} entity corresponding to the given DTO
     */
    User toEntity(RegisterRequest registerRequest);
}
