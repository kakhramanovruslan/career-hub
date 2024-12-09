package com.project.reviewservice.mapper;

import com.project.reviewservice.model.dto.ReviewDto;
import com.project.reviewservice.model.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewDtoMapper {

    ReviewDto toDto(Review review);

}
