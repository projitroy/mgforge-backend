package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.entity.WorkoutPlanInstanceEntity;
import com.mgforge.MGForge.enums.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutPlanInstanceRepository extends JpaRepository<WorkoutPlanInstanceEntity, UUID> {
    List<WorkoutPlanInstanceEntity> findAllByTenantIdAndClientId(UUID tenantId, UUID clientId);
    List<WorkoutPlanInstanceEntity> findAllByTenantIdAndClientIdAndStatus(UUID tenantId, UUID clientId, AssignmentStatus status);
    Optional<WorkoutPlanInstanceEntity> findByIdAndTenantId(UUID id, UUID tenantId);
}
