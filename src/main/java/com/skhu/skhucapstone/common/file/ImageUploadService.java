package com.skhu.skhucapstone.common.file;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    // subdirectory : 기능별 폴더명
    String upload(MultipartFile file, String subdirectory);
    void delete(String imagePath);
}
