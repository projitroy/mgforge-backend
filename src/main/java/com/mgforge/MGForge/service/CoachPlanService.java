package com.mgforge.MGForge.service;

import com.mgforge.MGForge.document.PlanDefinitionDocument;
import com.mgforge.MGForge.dto.CreateTrainingPlanInput;
import com.mgforge.MGForge.dto.UpdateTrainingPlanInput;
import com.mgforge.MGForge.entity.CoachClientLinkEntity;
import com.mgforge.MGForge.entity.WorkoutPlanInstanceEntity;
import com.mgforge.MGForge.enums.AssignmentStatus;
import com.mgforge.MGForge.exception.ResourceNotFoundException;
import com.mgforge.MGForge.repository.CoachClientLinkRepository;
import com.mgforge.MGForge.repository.PlanDefinitionRepository;
import com.mgforge.MGForge.repository.WorkoutPlanInstanceRepository;
import com.mgforge.MGForge.security.AppPrincipal;
import com.mgforge.MGForge.security.SecurityUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CoachPlanService {

    private final WorkoutPlanInstanceRepository workoutPlanInstanceRepository;
    private final PlanDefinitionRepository planDefinitionRepository;
    private final CoachClientLinkRepository coachClientLinkRepository;

    public CoachPlanService(WorkoutPlanInstanceRepository workoutPlanInstanceRepository, PlanDefinitionRepository planDefinitionRepository, CoachClientLinkRepository coachClientLinkRepository) {
        this.workoutPlanInstanceRepository = workoutPlanInstanceRepository;
        this.planDefinitionRepository = planDefinitionRepository;
        this.coachClientLinkRepository = coachClientLinkRepository;
    }

    @PreAuthorize("hasAnyRole('COACH','TENANT_ADMIN','ADMIN','SUPERADMIN') and @rbac.canAccessClient(T(java.util.UUID).fromString(#input.clientId))")
    public WorkoutPlanInstanceEntity createTrainingPlan(CreateTrainingPlanInput input){
        AppPrincipal principal = SecurityUtils.currentPrincipal();

        PlanDefinitionDocument planDefinition = new PlanDefinitionDocument();
        planDefinition.setTenantId(principal.getTenantId().toString());
        planDefinition.setName(input.getTitle());
        planDefinition.setSource(
                new PlanDefinitionDocument.SourceInfo("PERSONALIZED", null, null)
        );

        // later you can map input.getProgram() into fully typed weeks/day structure
        planDefinitionRepository.save(planDefinition);

        WorkoutPlanInstanceEntity entity = new WorkoutPlanInstanceEntity();
        entity.setTenantId(principal.getTenantId());
        entity.setClientId(UUID.fromString(input.getClientId()));
        entity.setAssignedById(principal.getUserId());
        entity.setTemplateId(null);
        entity.setStatus(input.getStatus() == null ?
                 AssignmentStatus.ACTIVE
                : AssignmentStatus.valueOf(input.getStatus()));
        entity.setMongoDefinitionId(planDefinition.getId()); // comes from mongo write result
        entity.setStartDate(input.getStartDate());
        entity.setEndDate(input.getEndDate());

        return workoutPlanInstanceRepository.save(entity);
    }

    @PreAuthorize("hasAnyRole('COACH','TENANT_ADMIN','ADMIN','SUPERADMIN') and @rbac.canAccessPlanInstance(#planInstanceId)")
    public WorkoutPlanInstanceEntity updatePlan(UUID planInstanceId, UpdateTrainingPlanInput input){
        WorkoutPlanInstanceEntity entity = workoutPlanInstanceRepository.findById(planInstanceId)
                .orElseThrow(()-> new AccessDeniedException("Plan not found"));

        if(input.getStatus() != null){
            entity.setStatus(input.getStatus());
        }
        if(input.getStartDate() != null){
            entity.setStartDate(input.getStartDate());
        }
        if(input.getEndDate() != null){
            entity.setEndDate(input.getEndDate());
        }

        return workoutPlanInstanceRepository.save(entity);
    }

    @PreAuthorize("isAuthenticated")
    public List<WorkoutPlanInstanceEntity> myPlanInstances(String status){
        AppPrincipal principal = SecurityUtils.currentPrincipal();

        if(status == null){
            return workoutPlanInstanceRepository.findAllByTenantIdAndClientId(
                    principal.getTenantId(),
                    principal.getUserId()
            );
        }

        return workoutPlanInstanceRepository.findAllByTenantIdAndClientIdAndStatus(
                principal.getTenantId(),
                principal.getUserId(),
                AssignmentStatus.valueOf(status)
        );
    }

    /**
     * Load Mongo plan structure by CockroachDB plan instance
     */
    @PreAuthorize(("hasRole('SUPERADMIN' or @rbac.canAccessPlanInstance(#planInstanceId)"))
    public PlanDefinitionDocument planDefinitionByInstance(UUID planInstanceId){
        WorkoutPlanInstanceEntity instance = workoutPlanInstanceRepository.findById(planInstanceId)
                .orElseThrow(()-> new ResourceNotFoundException("Plan instance not found"));

        return planDefinitionRepository.findById(instance.getMongoDefinitionId())
                .orElseThrow(()-> new ResourceNotFoundException("Plan definition not found"));
    }

    /**
     * Coach-specific helper to list their assigned client IDS
     */
    @PreAuthorize("hasRole('COACH')")
    public List<UUID> myClientIds(){
        AppPrincipal principal = SecurityUtils.currentPrincipal();

        List<CoachClientLinkEntity> links = coachClientLinkRepository.findAllByTenantIdAndCoachIdAndActiveTrue(
                principal.getTenantId(),
                principal.getUserId()
        );

        return links.stream()
                .map(CoachClientLinkEntity::getClientId)
                .toList();
    }
}
