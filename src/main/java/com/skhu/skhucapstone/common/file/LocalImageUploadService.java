package com.skhu.skhucapstone.common.file;

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
public class LocalImageUploadService implements ImageUploadService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    @Override
    public String upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다");
        }

        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        String subdirectory = "coffeechat";

        try {
            Path uploadPath = Paths.get(uploadDir + subdirectory);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(filename);
            file.transferTo(filePath.toFile());

            return "/" + subdirectory + "/" + filename;
        } catch (IOException e) {
            log.error("이미지 저장 실패: {}", e.getMessage());
            throw new RuntimeException("이미지 저장에 실패했습니다", e);
        }
    }

    @Override
    public void delete(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return;
        }

        try {
            Path filePath = Paths.get(uploadDir + imagePath.substring(1));
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("이미지 삭제 실패: {}", e.getMessage());
        }
    }
}
