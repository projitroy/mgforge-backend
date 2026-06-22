package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.entity.ExerciseMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExerciseMediaRepository extends JpaRepository<ExerciseMediaEntity, UUID> {
    List<ExerciseMediaEntity> findAllByTenantIdAndExerciseIdOrderBySortOrderAsc(UUID tenantId, UUID exerciseId);
}
