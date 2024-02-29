package com.example.tink_2_project.resource;

import com.example.tink_2_project.domain.Operation.OperationType;
import com.example.tink_2_project.dto.operation.OperationResponseDto;
import com.example.tink_2_project.mapper.OperationMapper;
import com.example.tink_2_project.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OperationResource {
    private final OperationService operationService;
    private final OperationMapper operationMapper;

    @GetMapping("/operation/{type}")
    public List<OperationResponseDto> getOperations(@PathVariable OperationType type) {
        return operationService.getOperationsByType(type).stream().map(operationMapper::toOperationResponseDto).toList();
    }
}
