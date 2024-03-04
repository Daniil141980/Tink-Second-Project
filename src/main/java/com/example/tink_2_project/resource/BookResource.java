package com.example.tink_2_project.resource;

import com.example.tink_2_project.dto.book.BookRequestDto;
import com.example.tink_2_project.dto.book.BookResponseDto;
import com.example.tink_2_project.dto.image.ImageResponseDto;
import com.example.tink_2_project.mapper.BookMapper;
import com.example.tink_2_project.mapper.ImageMapper;
import com.example.tink_2_project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookResource {
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final ImageMapper imageMapper;

    @PostMapping("/add")
    public BookResponseDto addBook(@RequestBody BookRequestDto bookRequestDto) {
        return bookMapper.toBookResponseDto(
                bookService.addBook(bookMapper.fromBookRequestDto(bookRequestDto),
                        bookRequestDto.imagesId() != null
                                ? bookRequestDto.imagesId()
                                : List.of()
                )
        );
    }

    @GetMapping("/books")
    public List<BookResponseDto> getBooks() {
        return bookService.getAllBooks().stream().map(bookMapper::toBookResponseDto).toList();
    }

    @GetMapping("/book/{id}")
    public BookResponseDto getBook(@PathVariable Long id) {
        return bookMapper.toBookResponseDto(bookService.getBook(id));
    }

    @GetMapping("/book/{id}/images")
    public List<ImageResponseDto> getBookImages(@PathVariable Long id) {
        return bookService.getBookImages(id).stream().map(imageMapper::toImageResponseDto).toList();
    }
}
