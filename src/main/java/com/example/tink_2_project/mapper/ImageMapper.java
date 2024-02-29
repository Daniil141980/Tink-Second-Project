package com.example.tink_2_project.mapper;

import com.example.tink_2_project.domain.ImageEntity;
import com.example.tink_2_project.dto.image.ImageResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageResponseDto toImageResponseDto(ImageEntity image);
}
