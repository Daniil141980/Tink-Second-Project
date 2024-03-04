package com.example.tink_2_project.repository;

import com.example.tink_2_project.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    boolean existsImageEntitiesByIdIn(List<Long> ids);

    boolean existsImageEntitiesByLink(String link);

    List<ImageEntity> findAllByIdIn(List<Long> ids);
}
