package com.project.authservice.controller;

import com.project.authservice.model.entity.ProfilePhoto;
import com.project.authservice.service.ProfilePhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/users/{userId}/profilePhoto")
@RestController
@RequiredArgsConstructor
public class ProfilePhotoController {

    private final ProfilePhotoService profilePhotoService;

    @PostMapping
    public ResponseEntity<String> uploadProfilePhoto(@PathVariable Long userId,
                                                     @RequestParam("file") MultipartFile file) throws Exception {
        profilePhotoService.saveAvatar(userId, file);
        return ResponseEntity.ok("Profile photo uploaded");
    }

    @GetMapping
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long userId) {
        ProfilePhoto profilePhoto = profilePhotoService.getAvatar(userId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(profilePhoto.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + profilePhoto.getFileName() + "\"")
                .body(profilePhoto.getFileData());
    }
}
