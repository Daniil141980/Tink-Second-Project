package com.example.tink_2_project.dto.operation;

import com.example.tink_2_project.domain.Operation.OperationType;

import java.time.LocalDateTime;

public record OperationResponseDto(String id,
                                   String content,
                                   LocalDateTime date,
                                   OperationType type) {
}
