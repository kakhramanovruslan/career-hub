package com.project.reviewservice.mapper;

import com.project.reviewservice.model.dto.ReviewRequest;
import com.project.reviewservice.model.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewRequestMapper {

    @Mapping(source = "senderId", target = "senderId")
    Review toEntity(ReviewRequest reviewRequest, Long senderId);

}
