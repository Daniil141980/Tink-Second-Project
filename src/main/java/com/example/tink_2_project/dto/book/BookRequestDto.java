package com.example.tink_2_project.dto.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record BookRequestDto(@NotNull
                             String name,
                             @NotNull
                             String author,
                             @JsonInclude(JsonInclude.Include.NON_NULL)
                             @JsonProperty("images_id")
                             List<Long> imagesId) {
}
