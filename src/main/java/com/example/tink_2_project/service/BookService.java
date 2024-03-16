package com.example.tink_2_project.service;

import com.example.tink_2_project.domain.BookEntity;
import com.example.tink_2_project.domain.ImageEntity;
import com.example.tink_2_project.domain.Operation;
import com.example.tink_2_project.domain.Operation.OperationType;
import com.example.tink_2_project.exception.EntityModelNotFoundException;
import com.example.tink_2_project.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final OperationService operationService;
    private final ImageService imageService;

    @Cacheable(value = "BookService::getAllBooks")
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    @CacheEvict(value = "BookService::getAllBooks", allEntries = true)
    public BookEntity addBook(BookEntity book, List<Long> imagesId) {
        if (!imagesId.isEmpty() && !imageService.existsAll(imagesId)) {
            throw new EntityModelNotFoundException("Изображения", "id", "?");
        }
        book.setImages(new ArrayList<>());
        imageService.getAllImages(imagesId).forEach(imageEntity -> imageEntity.addBook(book));
        operationService.logOperation(
                new Operation(
                        null,
                        String.format("Write book: %s", book),
                        LocalDateTime.now(),
                        OperationType.WRITE
                )
        );
        return bookRepository.save(book);
    }

    @Cacheable(value = "BookService::getBook", key = "#id")
    public BookEntity getBook(Long id) throws EntityModelNotFoundException {
        var book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new EntityModelNotFoundException("Книга", "id", Long.toString(id));
        }

        operationService.logOperation(
                new Operation(
                        null,
                        String.format("Read book: %s", book.get()),
                        LocalDateTime.now(),
                        OperationType.READ
                )
        );

        return book.get();
    }

    @Cacheable(value = "BookService::getBookImages", key = "#id + '.images'")
    public List<ImageEntity> getBookImages(Long id) {
        var book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new EntityModelNotFoundException("Книга", "id", Long.toString(id));
        }

        var images = book.get().getImages();
        operationService.logOperation(
                new Operation(
                        null,
                        String.format("Read book images: %s", images),
                        LocalDateTime.now(),
                        OperationType.READ
                )
        );

        return images;
    }
}
