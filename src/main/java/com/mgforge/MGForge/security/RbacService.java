package com.mgforge.MGForge.security;

import com.mgforge.MGForge.entity.WorkoutPlanInstanceEntity;
import com.mgforge.MGForge.repository.CoachClientLinkRepository;
import com.mgforge.MGForge.repository.UserRepository;
import com.mgforge.MGForge.repository.WorkoutPlanInstanceRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("rbac")
public class RbacService {

    private final UserRepository userRepository;
    private final CoachClientLinkRepository coachClientLinkRepository;
    private final WorkoutPlanInstanceRepository workoutPlanInstanceRepository;

    public RbacService(
            UserRepository userRepository,
            CoachClientLinkRepository coachClientLinkRepository,
            WorkoutPlanInstanceRepository workoutPlanInstanceRepository){
        this.userRepository = userRepository;
        this.coachClientLinkRepository = coachClientLinkRepository;
        this.workoutPlanInstanceRepository = workoutPlanInstanceRepository;
    }

    /**
     * Can current user manage/read a target user?
     *  - SUPERADMIN -> any tenant
     *  - TENANT_ADMIN / ADMIN -> only same tenant
     *  - COACH -> only assigned clients
     *  - USER -> only self
     */
    public boolean canAccessUser(UUID targetUserId){
        AppPrincipal principal = SecurityUtils.currentPrincipal();

        if(principal.hasRole("SUERADMIN")){
            return true;
        }

        if(principal.hasRole("USER")){
            return principal.getUserId().equals(targetUserId);
        }

        // Tenant admin / admin -> same tenant target
        if (principal.hasRole("TENANT_ADMIN") || principal.hasRole("ADMIN")){
            UUID tenantId = principal.getTenantId();
            return tenantId != null && userRepository.existsByIdAndTenantId(targetUserId, tenantId);
        }

        // Coach -> only assigned clients in same tenant
        if(principal.hasRole("COACH")) {
            UUID tenantId = principal.getTenantId();
            return tenantId != null && coachClientLinkRepository
                    .existsByTenantIdAndCoachIdAndClientIdAndActiveTrue(
                            tenantId,
                            principal.getUserId(),
                            targetUserId
                    );
        }

        return false;
    }

    /**
     * Same idea but more explicit for client access.
     */
    public boolean canAccessClient(UUID clientId){
        return canAccessUser(clientId);
    }

    /**
     * Can current user operate on this plan instance?
     * - SUPERADMIN -> yes
     * - TENANT_ADMIN / ADMIN -> only same tenant
     * - COACH -> assigned client only
     * - USER -> only own plan
     */
    public boolean canAccessPlanInstance(UUID plantInstanceId){
        AppPrincipal principal = SecurityUtils.currentPrincipal();

        WorkoutPlanInstanceEntity plan = workoutPlanInstanceRepository.findById(plantInstanceId)
                .orElseThrow(() -> new AccessDeniedException("Plan instance not found"));

        if(principal.hasRole("SUPERADMIN")){
            return true;
        }

        if(principal.getTenantId() == null || !principal.getTenantId().equals(plan.getTenantId())){
            return false;
        }

        if(principal.hasRole("TENANT_ADMIN") || principal.hasRole("ADMIN")){
            return true;
        }

        if(principal.hasRole("USER")){
            return principal.getUserId().equals(plan.getClientId());
        }

        if(principal.hasRole("COACH")){
            return coachClientLinkRepository.existsByTenantIdAndCoachIdAndClientIdAndActiveTrue(
                    plan.getTenantId(),
                    principal.getUserId(),
                    plan.getClientId()
            );
        }

        return false;

    }
}
