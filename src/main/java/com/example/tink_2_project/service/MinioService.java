package com.example.tink_2_project.service;

import com.example.tink_2_project.config.MinioProperties;
import com.example.tink_2_project.domain.ImageEntity;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
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
        boolean doesBucketExists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!doesBucketExists) {
            client.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build()
            );
        }
    }

    public ImageEntity uploadImage(MultipartFile file) throws Exception {
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

    public byte[] downloadImage(String link) throws Exception {
        return IOUtils.toByteArray(client.getObject(
                GetObjectArgs.builder()
                        .bucket(properties.getBucket())
                        .object(link)
                        .build()));
    }
}

