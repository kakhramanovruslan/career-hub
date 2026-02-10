package com.project.reviewservice.mapper;

import com.project.reviewservice.model.dto.ReviewUpdateRequest;
import com.project.reviewservice.model.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper interface for updating a {@link Review} entity based on a {@link ReviewUpdateRequest} DTO.
 * This interface is implemented by MapStruct at compile-time to provide efficient update logic.
 *
 * @see ReviewUpdateRequest
 * @see Review
 */
@Mapper(componentModel = "spring")
public interface ReviewUpdateRequestMapper {

    /**
     * Updates a {@link Review} entity with values from a {@link ReviewUpdateRequest} DTO.
     * The existing {@link Review} entity will be modified, rather than a new instance being created.
     *
     * @param request the {@link ReviewUpdateRequest} DTO containing the updated review data.
     * @param review the existing {@link Review} entity to be updated.
     * @return the updated {@link Review} entity.
     */
    Review updateReviewFromRequest(ReviewUpdateRequest request, @MappingTarget Review review);
}
