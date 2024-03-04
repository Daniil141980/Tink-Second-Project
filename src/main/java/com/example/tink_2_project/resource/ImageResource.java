package com.example.tink_2_project.resource;

import com.example.tink_2_project.dto.image.ImageResponseDto;
import com.example.tink_2_project.mapper.ImageMapper;
import com.example.tink_2_project.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ImageResource {
    private final ImageService imageService;
    private final ImageMapper imageMapper;

    @PostMapping("/load")
    public ImageResponseDto loadImage(MultipartFile file) {
        return imageMapper.toImageResponseDto(imageService.uploadImage(file));
    }

    @GetMapping(value = "/image/{link}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable String link) {
        return imageService.downloadImage(link);
    }

    @GetMapping("/image/{id}/meta")
    public ImageResponseDto getMeta(@PathVariable Long id) {
        return imageMapper.toImageResponseDto(imageService.getImageMeta(id));
    }
}
