package com.example.tink_2_project.service;

import com.example.tink_2_project.domain.ImageEntity;
import com.example.tink_2_project.domain.Operation;
import com.example.tink_2_project.domain.Operation.OperationType;
import com.example.tink_2_project.exception.EntityModelNotFoundException;
import com.example.tink_2_project.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final OperationService operationService;
    private final MinioService minioService;

    public Boolean existsAll(List<Long> imagesId) {
        return imageRepository.existsImageEntitiesByIdIn(imagesId);
    }

    public List<ImageEntity> getAllImages(List<Long> imagesId) {
        return imageRepository.findAllByIdIn(imagesId);
    }

    @Cacheable(value = "ImageService::getImageMeta", key = "#id")
    public ImageEntity getImageMeta(Long id) {
        var image = imageRepository.findById(id);
        if (image.isEmpty()) {
            throw new EntityModelNotFoundException("Изображение", "id", Long.toString(id));
        }

        operationService.logOperation(
                new Operation(null,
                        String.format("Read image metadata: %s", image.get()),
                        LocalDateTime.now(),
                        OperationType.READ
                )
        );

        return image.get();
    }

    public byte[] downloadImage(String link) throws Exception {
        if (!imageRepository.existsImageEntitiesByLink(link)) {
            throw new EntityModelNotFoundException("Изображение", "link", link);
        }
        return minioService.downloadImage(link);
    }

    @Cacheable(value = "ImageService::uploadImage", key = "#file.getOriginalFilename()")
    public ImageEntity uploadImage(MultipartFile file) throws Exception {
        var image = minioService.uploadImage(file);
        imageRepository.save(image);
        operationService.logOperation(
                new Operation(null,
                        String.format("Upload image: %s", image),
                        LocalDateTime.now(),
                        OperationType.WRITE
                )
        );
        return image;
    }
}
