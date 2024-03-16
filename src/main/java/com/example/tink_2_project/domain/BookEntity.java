package com.example.tink_2_project.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private List<ImageEntity> images;

}