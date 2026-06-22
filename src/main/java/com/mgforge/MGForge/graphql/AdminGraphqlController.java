package com.mgforge.MGForge.graphql;

import com.mgforge.MGForge.dto.CreateUserInput;
import com.mgforge.MGForge.dto.UpdateUserInput;
import com.mgforge.MGForge.entity.UserEntity;
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

    public AdminGraphqlController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
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
}
