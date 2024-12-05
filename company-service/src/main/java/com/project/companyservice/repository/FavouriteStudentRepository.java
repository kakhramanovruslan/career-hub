package com.project.companyservice.repository;

import com.project.companyservice.model.entity.FavouriteStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteStudentRepository extends JpaRepository<FavouriteStudent, Long> {
    void deleteFavouriteStudentByOwnerIdAndStudentId(Long ownerId, Long studentId);
    List<FavouriteStudent> findFavouriteStudentsByOwnerId(Long ownerId);
}
