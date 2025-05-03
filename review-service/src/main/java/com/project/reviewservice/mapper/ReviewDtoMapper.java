package com.project.reviewservice.mapper;

import com.project.reviewservice.model.dto.ReviewDto;
import com.project.reviewservice.model.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between {@link Review} entity and {@link ReviewDto} DTO.
 * This interface is implemented by MapStruct at compile-time to provide efficient mapping logic.
 *
 * @see Review
 * @see ReviewDto
 */
@Mapper(componentModel = "spring")
public interface ReviewDtoMapper {

    /**
     * Converts a {@link Review} entity to a {@link ReviewDto} DTO.
     * This method will be used to map a Review object into a data transfer object that can be returned in API responses.
     *
     * @param review the {@link Review} entity to be converted.
     * @return the corresponding {@link ReviewDto} object.
     */
    ReviewDto toDto(Review review);
}
