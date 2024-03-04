package com.example.tink_2_project.mapper;

import com.example.tink_2_project.domain.Operation;
import com.example.tink_2_project.dto.operation.OperationResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OperationMapper {
    OperationResponseDto toOperationResponseDto(Operation operation);
}
