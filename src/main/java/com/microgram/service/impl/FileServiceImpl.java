package com.microgram.service.impl;

import com.microgram.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${app.upload.avatars:uploads/avatars}")
    private String avatarsDir;

    @Value("${app.upload.posts:uploads/posts}")
    private String postsDir;

    @Override
    public String saveAvatar(MultipartFile file, String username) {
        if (file == null || file.isEmpty()) {
            log.warn("Попытка сохранить пустой файл аватара для: {}", username);
            return "default.png";
        }

        if (!isImage(file)) {
            log.warn("Файл аватара не является изображением: {}", file.getOriginalFilename());
            return "default.png";
        }

        String fileName = username + "_"
                + UUID.randomUUID()
                + getExtension(file.getOriginalFilename());

        saveFile(file, avatarsDir, fileName);

        log.info("Аватар сохранён: {}", fileName);
        return fileName;
    }

    @Override
    public String savePostImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            log.warn("Попытка сохранить пустое изображение поста");
            throw new RuntimeException("Файл изображения пустой");
        }

        if (!isImage(file)) {
            log.warn("Файл поста не является изображением: {}", file.getOriginalFilename());
            throw new RuntimeException("Файл должен быть изображением");
        }

        String fileName = UUID.randomUUID()
                + getExtension(file.getOriginalFilename());

        saveFile(file, postsDir, fileName);

        log.info("Изображение поста сохранено: {}", fileName);
        return fileName;
    }

    private void saveFile(MultipartFile file, String dir, String fileName) {
        try {
            Path dirPath = Paths.get(dir).toAbsolutePath().normalize();

            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                log.info("Создана директория для загрузок: {}", dirPath);
            }

            Path filePath = dirPath.resolve(fileName).toAbsolutePath().normalize();

            file.transferTo(filePath);

            log.debug("Файл успешно записан по пути: {}", filePath);

        } catch (IOException e) {
            log.error("Ошибка сохранения файла {}: {}", fileName, e.getMessage(), e);
            throw new RuntimeException("Не удалось сохранить файл: " + fileName, e);
        }
    }

    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    private String getExtension(String originalFileName) {
        if (originalFileName == null || !originalFileName.contains(".")) {
            return ".jpg";
        }
        return originalFileName
                .substring(originalFileName.lastIndexOf("."))
                .toLowerCase();
    }
}