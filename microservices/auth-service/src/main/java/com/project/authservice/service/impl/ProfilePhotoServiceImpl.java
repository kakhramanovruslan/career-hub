package com.project.authservice.service.impl;

import com.project.authservice.model.entity.ProfilePhoto;
import com.project.authservice.repository.ProfilePhotoRepository;
import com.project.authservice.service.ProfilePhotoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfilePhotoServiceImpl implements ProfilePhotoService {

    private final ProfilePhotoRepository repository;

    @Override
    @Transactional
    public ProfilePhoto saveAvatar(Long userId, MultipartFile file) throws IOException {
        ProfilePhoto avatar = repository.findByUserId(userId)
                .orElseGet(ProfilePhoto::new);

        avatar.setUserId(userId);
        avatar.setFileData(file.getBytes());
        avatar.setContentType(file.getContentType());
        avatar.setFileName(file.getOriginalFilename());
        avatar.setCreatedAt(java.time.LocalDateTime.now());

        return repository.save(avatar);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfilePhoto getAvatar(Long userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Avatar not found for user " + userId));
    }
}
