package com.example.tink_2_project.repository;

import com.example.tink_2_project.domain.Operation;
import com.example.tink_2_project.domain.Operation.OperationType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OperationRepository extends MongoRepository<Operation, String> {
    List<Operation> findAllByType(OperationType type);
}
