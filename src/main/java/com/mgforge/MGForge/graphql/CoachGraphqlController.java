package com.mgforge.MGForge.graphql;

import com.mgforge.MGForge.document.CommentDocument;
import com.mgforge.MGForge.document.PlanDefinitionDocument;
import com.mgforge.MGForge.document.WorkoutLogDocument;
import com.mgforge.MGForge.dto.CreateCommentInput;
import com.mgforge.MGForge.dto.CreateTrainingPlanInput;
import com.mgforge.MGForge.dto.CreateWorkoutLogInput;
import com.mgforge.MGForge.dto.UpdateTrainingPlanInput;
import com.mgforge.MGForge.entity.WorkoutPlanInstanceEntity;
import com.mgforge.MGForge.service.CoachPlanService;
import com.mgforge.MGForge.service.CommentService;
import com.mgforge.MGForge.service.WorkoutLogService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.UUID;

@Controller
public class CoachGraphqlController {

    private final CoachPlanService coachPlanService;
    private final WorkoutLogService workoutLogService;
    private final CommentService commentService;

    public CoachGraphqlController(CoachPlanService coachPlanService, WorkoutLogService workoutLogService, CommentService commentService) {
        this.coachPlanService = coachPlanService;
        this.workoutLogService = workoutLogService;
        this.commentService = commentService;
    }

    @MutationMapping
    public WorkoutPlanInstanceEntity createTrainingPlan(@Argument CreateTrainingPlanInput input){
        return coachPlanService.createTrainingPlan(input);
    }

    @MutationMapping
    public WorkoutPlanInstanceEntity updateTrainingPlan(@Argument UpdateTrainingPlanInput input,@Argument UUID planInstanceId){
        return coachPlanService.updatePlan(planInstanceId,input);
    }

    @QueryMapping
    public List<WorkoutPlanInstanceEntity> myPlanInstance(@Argument String status){
        return coachPlanService.myPlanInstances(status);
    }

    @QueryMapping
    public PlanDefinitionDocument planDefinitionByInstance(@Argument UUID planInstanceId){
        return coachPlanService.planDefinitionByInstance(planInstanceId);
    }

    @MutationMapping
    public WorkoutLogDocument createWorkoutLog(@Argument CreateWorkoutLogInput input){
        return workoutLogService.createWorkoutLog(input);
    }

    @QueryMapping
    public List<WorkoutLogDocument> workoutLogsByClient(@Argument String clientId){
        return workoutLogService.workoutLogsByClient(clientId);
    }

    @MutationMapping
    public CommentDocument createDocument(@Argument CreateCommentInput input){
        return commentService.createComment(input);
    }

    @QueryMapping
    public List<CommentDocument> comments(@Argument String targetType, @Argument String targetId){
        return commentService.comments(targetType,targetId);
    }

}
