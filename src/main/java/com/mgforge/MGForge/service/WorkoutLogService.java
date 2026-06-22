package com.mgforge.MGForge.service;

import com.mgforge.MGForge.document.WorkoutLogDocument;
import com.mgforge.MGForge.dto.CreateWorkoutLogInput;
import com.mgforge.MGForge.entity.WorkoutPlanInstanceEntity;
import com.mgforge.MGForge.exception.ResourceNotFoundException;
import com.mgforge.MGForge.repository.WorkoutLogRepository;
import com.mgforge.MGForge.repository.WorkoutPlanInstanceRepository;
import com.mgforge.MGForge.security.AppPrincipal;
import com.mgforge.MGForge.security.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;
    private final WorkoutPlanInstanceRepository workoutPlanInstanceRepository;

    public WorkoutLogService(WorkoutLogRepository workoutLogRepository, WorkoutPlanInstanceRepository workoutPlanInstanceRepository) {
        this.workoutLogRepository = workoutLogRepository;
        this.workoutPlanInstanceRepository = workoutPlanInstanceRepository;
    }

    @PreAuthorize("hasRole('SUPERADMIN') or @rbac.canAccessPlanInstance(T(java.util.UUID).fromString(#input.planInstanceId))")
    public WorkoutLogDocument createWorkoutLog(CreateWorkoutLogInput input){
        AppPrincipal principal = SecurityUtils.currentPrincipal();

        WorkoutPlanInstanceEntity instance =
                workoutPlanInstanceRepository.findById(UUID.fromString(input.getPlanInstanceId()))
                        .orElseThrow(()->new ResourceNotFoundException("Plan Instance not found"));

        WorkoutLogDocument doc = new WorkoutLogDocument();
        doc.setTenantId(principal.getTenantId().toString());
        doc.setClientId(instance.getClientId().toString());
        doc.setCoachId(principal.hasRole("COACH") ? principal.getUserId().toString():null);
        doc.setPlanInstanceId(instance.getId().toString());
        doc.setWeekNumber(input.getWeekNumber());
        doc.setDayNumber(input.getDayNumber());
        doc.setCompletedAt(input.getCompletedAt());
        doc.setClientNotes(input.getClientNotes());
        doc.setCoachCommentIds(List.of());

        return workoutLogRepository.save(doc);
    }

    @PreAuthorize("hasRole('SUPERADMIN') or @rbac.canAccessClient(T(java.util.UUID).fromString(#clientId))")
    public List<WorkoutLogDocument> workoutLogsByClient(String clientId){
        AppPrincipal principal = SecurityUtils.currentPrincipal();
        return workoutLogRepository.findAllByTenantAndClientOrderByCompletedAtDesc(
                principal.getTenantId().toString(),
                clientId
        );
    }

}
