package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, UUID> {
    List<ExerciseEntity> findAllByTenantId(UUID tenantId);
}
