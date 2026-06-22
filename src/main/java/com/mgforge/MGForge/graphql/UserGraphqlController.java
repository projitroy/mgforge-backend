package com.mgforge.MGForge.graphql;

import com.mgforge.MGForge.entity.UserEntity;
import com.mgforge.MGForge.service.UserSelfService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserGraphqlController {

    private final UserSelfService userSelfService;

    public UserGraphqlController(UserSelfService userSelfService) {
        this.userSelfService = userSelfService;
    }

    @QueryMapping
    public UserEntity me(){
        return userSelfService.me();
    }
}
