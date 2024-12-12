package com.project.reviewservice.mapper;

import com.project.reviewservice.model.dto.ReviewRequest;
import com.project.reviewservice.model.entity.Review;
import com.project.reviewservice.model.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewRequestMapper {

    @Mapping(source = "senderId", target = "senderId")
    @Mapping(source = "senderRole", target = "senderRole")
    Review toEntity(ReviewRequest reviewRequest, Long senderId, UserRole senderRole);

}
