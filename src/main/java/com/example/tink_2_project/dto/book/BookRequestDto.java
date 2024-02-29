package com.example.tink_2_project.dto.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BookRequestDto(String name,
                             String author,
                             @JsonInclude(JsonInclude.Include.NON_NULL)
                             @JsonProperty("images_id")
                             List<Long> imagesId) {
}
