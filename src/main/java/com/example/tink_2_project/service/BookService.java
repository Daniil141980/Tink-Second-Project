package com.example.tink_2_project.service;

import com.example.tink_2_project.domain.BookEntity;
import com.example.tink_2_project.exception.EntityModelNotFoundException;
import com.example.tink_2_project.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public BookEntity addBook(BookEntity book) {
        return bookRepository.save(book);
    }

    public BookEntity getBook(Long id) throws EntityModelNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new EntityModelNotFoundException("Книги", "id", Long.toString(id)));
    }
}
