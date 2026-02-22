package com.project.reviewservice.mapper;

import com.project.reviewservice.model.dto.ReviewRequest;
import com.project.reviewservice.model.entity.Review;
import com.project.reviewservice.model.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between {@link ReviewRequest} DTO and {@link Review} entity.
 * This interface is implemented by MapStruct at compile-time to provide efficient mapping logic.
 *
 * @see ReviewRequest
 * @see Review
 */
@Mapper(componentModel = "spring")
public interface ReviewRequestMapper {

    /**
     * Converts a {@link ReviewRequest} DTO to a {@link Review} entity.
     * This method takes additional parameters for the sender's ID and role to set them in the resulting entity.
     *
     * @param reviewRequest the {@link ReviewRequest} DTO to be converted.
     * @param senderId the ID of the sender of the review.
     * @param senderRole the role of the sender of the review.
     * @return the corresponding {@link Review} entity.
     */
    @Mapping(source = "senderId", target = "senderId")
    @Mapping(source = "senderRole", target = "senderRole")
    Review toEntity(ReviewRequest reviewRequest, Long senderId, UserRole senderRole);
}
