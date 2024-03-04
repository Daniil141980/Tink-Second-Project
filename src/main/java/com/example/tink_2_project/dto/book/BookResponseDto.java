package com.example.tink_2_project.dto.book;

import com.example.tink_2_project.dto.image.ImageResponseDto;

import java.util.List;

public record BookResponseDto(Long id,
                              String name,
                              String author,
                              List<ImageResponseDto> images) {
}
