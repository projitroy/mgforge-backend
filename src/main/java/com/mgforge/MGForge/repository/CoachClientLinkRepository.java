package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.entity.CoachClientLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CoachClientLinkRepository extends JpaRepository<CoachClientLinkEntity, UUID>
{
    boolean existsByTenantIdAndCoachIdAndClientIdAndActiveTrue(
            UUID tenantId,
            UUID coachId,
            UUID clientId
    );
    List<CoachClientLinkEntity> findAllByTenantIdAndCoachIdAndActiveTrue(UUID tenantId, UUID coachId);
}
