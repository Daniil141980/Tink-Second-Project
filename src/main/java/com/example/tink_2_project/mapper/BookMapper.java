package com.example.tink_2_project.mapper;

import com.example.tink_2_project.domain.BookEntity;
import com.example.tink_2_project.dto.book.BookRequestDto;
import com.example.tink_2_project.dto.book.BookResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface BookMapper {

    BookResponseDto toBookResponseDto(BookEntity book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    BookEntity fromBookRequestDto(BookRequestDto dto);
}

