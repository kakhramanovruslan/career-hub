package com.project.companyservice.service;

import com.project.companyservice.model.entity.FavouriteStudent;

import java.util.List;

/**
 * Service interface for managing favourite students for a company.
 */
public interface FavouriteStudentService {

    /**
     * Adds a student to the company's list of favourite students.
     */
    FavouriteStudent addFavouriteStudent(Long id, Long studentOwnerId, Long userId);

    /**
     * Deletes a student from the company's list of favourite students.
     */
    void deleteFavouriteStudent(Long id, Long studentOwnerId, Long userId);

    /**
     * Retrieves the list of favourite students for a given company.
     */
    List<Long> getFavouriteStudentsByOwnerId(Long id, Long userId);
}
