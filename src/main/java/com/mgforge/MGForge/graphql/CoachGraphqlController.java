package com.mgforge.MGForge.graphql;

import com.mgforge.MGForge.entity.WorkoutPlanInstanceEntity;
import com.mgforge.MGForge.service.CoachPlanService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class CoachGraphqlController {

    private final CoachPlanService coachPlanService;

    public CoachGraphqlController(CoachPlanService coachPlanService) {
        this.coachPlanService = coachPlanService;
    }

    @MutationMapping
    public WorkoutPlanInstanceEntity createPersonalPlan(@Argument CreateTrainingPlanInput input){
        return coachPlanService.createPersonalPlan(input);
    }

    @MutationMapping
    public WorkoutPlanInstanceEntity updateTrainingPlan(@Argument UUID id, @Argument UpdateTrainingPlanInput input){
        return coachPlanService.updatePlan(id,input);
    }
}
