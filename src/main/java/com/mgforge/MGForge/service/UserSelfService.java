package com.mgforge.MGForge.service;

import com.mgforge.MGForge.dto.AuthContextDto;
import com.mgforge.MGForge.entity.UserEntity;
import com.mgforge.MGForge.repository.UserRepository;
import com.mgforge.MGForge.security.AppPrincipal;
import com.mgforge.MGForge.security.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserSelfService {

    private final UserRepository userRepository;

    public UserSelfService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("isAuthenticated()")
    public UserEntity me(){
        AppPrincipal principal = SecurityUtils.currentPrincipal();
        return userRepository.findById(principal.getUserId())
                .orElseThrow();
    }

    @PreAuthorize("isAuthenticated()")
    public AuthContextDto authContext(){
        AppPrincipal principal = SecurityUtils.currentPrincipal();

        return new AuthContextDto(principal.getTenantId() == null ? null :
                principal.getTenantId().toString(),
                principal.getRoles());
    }
}
