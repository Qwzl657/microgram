package com.microgram.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveAvatar(MultipartFile file, String username);

    String savePostImage(MultipartFile file);
}