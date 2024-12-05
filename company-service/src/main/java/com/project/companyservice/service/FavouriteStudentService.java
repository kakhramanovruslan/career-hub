package com.project.companyservice.service;

import com.project.companyservice.model.entity.FavouriteStudent;

import java.util.List;

public interface FavouriteStudentService {

    FavouriteStudent addFavouriteStudent(Long id, Long studentOwnerId, Long userId);
    void deleteFavouriteStudent(Long id, Long studentOwnerId, Long userId);

    List<Long> getFavouriteStudentsByOwnerId(Long id, Long userId);
}
