package com.project.authservice.service;

import com.project.authservice.model.entity.ProfilePhoto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfilePhotoService {

    ProfilePhoto saveAvatar(Long userId, MultipartFile file) throws IOException;

    ProfilePhoto getAvatar(Long userId);
}
