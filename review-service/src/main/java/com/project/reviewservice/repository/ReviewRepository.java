package com.project.reviewservice.repository;

import com.project.reviewservice.model.dto.AverageRatingResponse;
import com.project.reviewservice.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByRecipientId(Long recipientId, Pageable pageable);

    @Query("SELECT COALESCE(AVG(r.rating), 0.0) FROM Review r WHERE r.recipientId = :recipientId")
    Double getAverageRatingByRecipientId(@Param("recipientId") Long recipientId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.recipientId = :recipientId")
    Long getTotalCountByRecipientId(@Param("recipientId") Long recipientId);

}
