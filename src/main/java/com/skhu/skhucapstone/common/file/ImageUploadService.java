package com.skhu.skhucapstone.common.file;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    String upload(MultipartFile file);
    void delete(String imagePath);
}
