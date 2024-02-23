package com.example.tink_2_project.mapper;

import com.example.tink_2_project.domain.BookEntity;
import com.example.tink_2_project.dto.book.BookRequestDto;
import com.example.tink_2_project.dto.book.BookResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResponseDto toBookResponseDto(BookEntity subject);

    @Mapping(target = "id", ignore = true)
    BookEntity fromBookRequestDto(BookRequestDto dto);
}

