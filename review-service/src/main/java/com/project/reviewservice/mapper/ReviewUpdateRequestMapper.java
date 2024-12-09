package com.project.reviewservice.mapper;

import com.project.reviewservice.model.dto.ReviewUpdateRequest;
import com.project.reviewservice.model.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewUpdateRequestMapper {

    Review updateReviewFromRequest(ReviewUpdateRequest request, @MappingTarget Review review);
}
