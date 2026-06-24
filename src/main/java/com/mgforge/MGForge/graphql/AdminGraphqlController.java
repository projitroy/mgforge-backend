package com.mgforge.MGForge.graphql;

import com.mgforge.MGForge.dto.AssignRoleInput;
import com.mgforge.MGForge.dto.CreateExerciseInput;
import com.mgforge.MGForge.dto.CreateUserInput;
import com.mgforge.MGForge.dto.UpdateUserInput;
import com.mgforge.MGForge.entity.ExerciseEntity;
import com.mgforge.MGForge.entity.ExerciseMediaEntity;
import com.mgforge.MGForge.entity.TenantEntity;
import com.mgforge.MGForge.entity.UserEntity;
import com.mgforge.MGForge.service.ExerciseService;
import com.mgforge.MGForge.service.TenantService;
import com.mgforge.MGForge.service.admin.AdminUserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class AdminGraphqlController {

    private final AdminUserService adminUserService;
    private final TenantService tenantService;
    private final ExerciseService exerciseService;

    public AdminGraphqlController(AdminUserService adminUserService, TenantService tenantService, ExerciseService exerciseService) {
        this.adminUserService = adminUserService;
        this.tenantService = tenantService;
        this.exerciseService = exerciseService;
    }

    @QueryMapping
    public TenantEntity currentTenant(){
        return tenantService.currentTenant();
    }

    @QueryMapping
    public List<TenantEntity> tenants(){
        return tenantService.tenants();
    }

    @QueryMapping
    public List<UserEntity> tenantUsers(){
        return adminUserService.tenantUsers();
    }

    @QueryMapping
    public List<UserEntity> usersByTenant(@Argument UUID tenantId){
        return adminUserService.getUsersByTenant(tenantId);
    }

    @MutationMapping
    public UserEntity createUser(@Argument CreateUserInput input){
        return adminUserService.createUser(input);
    }

    @MutationMapping
    public UserEntity updateUser(@Argument UUID userId, @Argument UpdateUserInput input){
        return adminUserService.updateUser(userId, input);
    }

    @MutationMapping
    public UserEntity assignRoles(@Argument AssignRoleInput input){
        return adminUserService.assignRoles(input);
    }

    @QueryMapping
    public List<ExerciseEntity> tenantExercises(){
        return exerciseService.tenantExercises();
    }

    @MutationMapping
    public ExerciseEntity createExercise(@Argument CreateExerciseInput input){
        return exerciseService.createExercise(input);
    }

    @QueryMapping
    public List<ExerciseMediaEntity> exerciseMedia(@Argument UUID exerciseId){
        return exerciseService.exerciseMedia(exerciseId);
    }
}
