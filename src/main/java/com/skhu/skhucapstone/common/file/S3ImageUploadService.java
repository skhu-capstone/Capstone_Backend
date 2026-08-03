package com.skhu.skhucapstone.common.file;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "file.upload.type", havingValue = "s3")
public class S3ImageUploadService implements ImageUploadService {

    private final S3Client s3Client;

    @Value("${cloud.oci.region}")
    private String region;

    @Value("${cloud.oci.namespace}")
    private String namespace;

    @Value("${cloud.oci.bucket}")
    private String bucket;

    private String publicUrlPrefix;

    @PostConstruct
    void initPublicUrlPrefix() {
        publicUrlPrefix = "https://objectstorage." + region + ".oraclecloud.com"
                + "/n/" + namespace + "/b/" + bucket + "/o/";
    }

    @Override
    public String upload(MultipartFile file, String subdirectory) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다");
        }

        String key = subdirectory + "/" + UUID.randomUUID() + extensionOf(file.getOriginalFilename());

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentTypeOf(file))
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException | S3Exception e) {
            log.error("이미지 업로드 실패 (key={}): {}", key, e.getMessage());
            throw new RuntimeException("이미지 저장에 실패했습니다", e);
        }

        return publicUrlPrefix + URLEncoder.encode(key, StandardCharsets.UTF_8).replace("+", "%20");
    }

    @Override
    public void delete(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }

        if (!imageUrl.startsWith(publicUrlPrefix)) {
            log.debug("오브젝트 스토리지 대상이 아니라 삭제를 건너뜁니다: {}", imageUrl);
            return;
        }

        String key = URLDecoder.decode(
                imageUrl.substring(publicUrlPrefix.length()), StandardCharsets.UTF_8);

        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build());
        } catch (S3Exception e) {
            log.error("이미지 삭제 실패 (key={}): {}", key, e.getMessage());
        }
    }

    private String extensionOf(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }
        int dot = originalFilename.lastIndexOf('.');
        return (dot == -1) ? "" : originalFilename.substring(dot).toLowerCase();
    }

    private String contentTypeOf(MultipartFile file) {
        String contentType = file.getContentType();
        return (contentType == null || contentType.isBlank())
                ? "application/octet-stream"
                : contentType;
    }
}
