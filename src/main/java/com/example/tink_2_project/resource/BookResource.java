package com.example.tink_2_project.resource;

import com.example.tink_2_project.dto.book.BookRequestDto;
import com.example.tink_2_project.dto.book.BookResponseDto;
import com.example.tink_2_project.mapper.BookMapper;
import com.example.tink_2_project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookResource {
    private final BookService bookService;
    private final BookMapper bookMapper;

    @MutationMapping
    public BookResponseDto addBook(@Argument BookRequestDto bookRequestDto) {
        return bookMapper.toBookResponseDto(bookService.addBook(bookMapper.fromBookRequestDto(bookRequestDto)));
    }

    @QueryMapping
    public List<BookResponseDto> getBooks() {
        return bookService.getAllBooks().stream().map(bookMapper::toBookResponseDto).toList();
    }

    @QueryMapping
    public BookResponseDto getBook(@Argument Long id) {
        return bookMapper.toBookResponseDto(bookService.getBook(id));
    }
}
