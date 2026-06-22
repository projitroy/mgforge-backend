package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.entity.WorkoutPlanInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkoutPlanInstanceRepository extends JpaRepository<WorkoutPlanInstanceEntity, UUID> {

    Optional<WorkoutPlanInstanceEntity> findByIdAndTenantId(UUID id, UUID tenantId);
}
