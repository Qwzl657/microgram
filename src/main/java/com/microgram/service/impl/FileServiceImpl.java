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

    @Value("${app.upload.avatars:src/main/resources/static/avatars}")
    private String avatarsDir;

    @Value("${app.upload.posts:src/main/resources/static/posts}")
    private String postsDir;

    @Override
    public String saveAvatar(MultipartFile file, String username) {
        String fileName = username + "_" + UUID.randomUUID()
                + getExtension(file.getOriginalFilename());

        saveFile(file, avatarsDir, fileName);
        log.info("Аватар сохранён: {}", fileName);
        return fileName;
    }

    @Override
    public String savePostImage(MultipartFile file) {
        String fileName = UUID.randomUUID()
                + getExtension(file.getOriginalFilename());

        saveFile(file, postsDir, fileName);
        log.info("Изображение поста сохранено: {}", fileName);
        return fileName;
    }

    private void saveFile(MultipartFile file, String dir, String fileName) {
        try {
            Path dirPath = Paths.get(dir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                log.debug("Создана папка для файлов: {}", dirPath);
            }

            Path filePath = dirPath.resolve(fileName);
            file.transferTo(filePath.toFile());

        } catch (IOException e) {
            log.error("Ошибка сохранения файла {}: {}", fileName, e.getMessage());
            throw new RuntimeException("Не удалось сохранить файл: " + fileName);
        }
    }

    private String getExtension(String originalFileName) {
        if (originalFileName == null || !originalFileName.contains(".")) {
            return ".jpg";
        }
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }
}