package com.example.tink_2_project.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ImageEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, name = "name")
    private String name;

    @Column(name = "size")
    private Integer size;

    @Column(length = 300, name = "link")
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @ToString.Exclude
    private BookEntity book;

    public void addBook(BookEntity book) {
        this.setBook(book);
        book.getImages().add(this);
    }
}