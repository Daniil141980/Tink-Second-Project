package com.example.tink_2_project.service;

import com.example.tink_2_project.domain.Operation;
import com.example.tink_2_project.domain.Operation.OperationType;
import com.example.tink_2_project.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;

    public void logOperation(Operation operation) {
        operationRepository.save(operation);
    }

    public List<Operation> getOperationsByType(OperationType type) {
        return operationRepository.findAllByType(type);
    }
}

