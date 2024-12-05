package com.project.companyservice.service.impl;

import com.project.companyservice.exception.AccessDeniedException;
import com.project.companyservice.exception.CompanyNotFoundException;
import com.project.companyservice.model.entity.Company;
import com.project.companyservice.model.entity.FavouriteStudent;
import com.project.companyservice.repository.CompanyRepository;
import com.project.companyservice.repository.FavouriteStudentRepository;
import com.project.companyservice.service.FavouriteStudentService;
import com.project.companyservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavouriteStudentServiceImpl implements FavouriteStudentService {
    private final FavouriteStudentRepository favStudentRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public FavouriteStudent addFavouriteStudent(Long id, Long studentOwnerId, Long userId) {
        isOwner(userId, id);
        Company company = companyRepository.findCompanyByOwnerId(id)
                .orElseThrow(() -> new CompanyNotFoundException(ExceptionMessages.COMPANY_NOT_FOUND));

        FavouriteStudent favouriteStudent = FavouriteStudent.builder()
                .ownerId(id)
                .company(company)
                .studentId(studentOwnerId)
                .build();

        return favStudentRepository.save(favouriteStudent);
    }

    @Override
    @Transactional
    public void deleteFavouriteStudent(Long id, Long studentOwnerId, Long userId) {
        isOwner(userId, id);
        companyRepository.findCompanyByOwnerId(id)
                .orElseThrow(() -> new CompanyNotFoundException(ExceptionMessages.COMPANY_NOT_FOUND));

        favStudentRepository.deleteFavouriteStudentByOwnerIdAndStudentId(id, studentOwnerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getFavouriteStudentsByOwnerId(Long id, Long userId) {
        isOwner(id, userId);
        companyRepository.findCompanyByOwnerId(id)
                .orElseThrow(() -> new CompanyNotFoundException(ExceptionMessages.COMPANY_NOT_FOUND));

        return favStudentRepository
                .findFavouriteStudentsByOwnerId(id)
                .stream()
                .map(FavouriteStudent::getStudentId)
                .collect(Collectors.toList());
    }

    private void isOwner(Long id, Long ownerId) throws AccessDeniedException {
        if (!id.equals(ownerId)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
