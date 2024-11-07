package com.project.authservice.mapper;

import com.project.authservice.model.dto.RegisterRequest;
import com.project.authservice.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterRequestMapper {

    User toEntity(RegisterRequest registerRequest);
}
