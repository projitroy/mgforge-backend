package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.entity.WorkoutPlanTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutPlanTemplateRepository extends JpaRepository<WorkoutPlanTemplateEntity, UUID> {
    List<WorkoutPlanTemplateEntity> findAllByTenantId(UUID tenantId);
}
