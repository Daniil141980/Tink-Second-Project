package com.example.tink_2_project.service;

import com.example.tink_2_project.config.MinioProperties;
import com.example.tink_2_project.domain.ImageEntity;
import com.example.tink_2_project.exception.StorageException;
import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient client;
    private final MinioProperties properties;

    @SneakyThrows
    @PostConstruct
    public void init() {
        var bucketName = properties.getBucket();
        if (Objects.isNull(bucketName) || bucketName.isBlank()) {
            throw new StorageException("You should specify bucket name to use storage");
        }
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            client.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build()
            );
        }
    }

    @SneakyThrows
    public ImageEntity uploadImage(MultipartFile file) {
        var fileId = UUID.randomUUID().toString();
        client.putObject(
                PutObjectArgs.builder()
                        .bucket(properties.getBucket())
                        .object(fileId)
                        .stream(new ByteArrayInputStream(file.getBytes()), file.getSize(), properties.getImageSize())
                        .contentType(file.getContentType())
                        .build()
        );

        return new ImageEntity(null, file.getOriginalFilename(), (int) file.getSize(), fileId, null);
    }

    @SneakyThrows
    public byte[] downloadImage(String link) {
        return IOUtils.toByteArray(client.getObject(
                GetObjectArgs.builder()
                        .bucket(properties.getBucket())
                        .object(link)
                        .build()));
    }
}

