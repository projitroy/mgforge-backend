package com.mgforge.MGForge.service.admin;

import com.mgforge.MGForge.dto.AssignRoleInput;
import com.mgforge.MGForge.dto.CreateUserInput;
import com.mgforge.MGForge.dto.UpdateUserInput;
import com.mgforge.MGForge.entity.UserEntity;
import com.mgforge.MGForge.entity.UserRoleEntity;
import com.mgforge.MGForge.enums.RoleType;
import com.mgforge.MGForge.enums.UserStatus;
import com.mgforge.MGForge.exception.AccountDisabledException;
import com.mgforge.MGForge.exception.ResourceNotFoundException;
import com.mgforge.MGForge.repository.UserRepository;
import com.mgforge.MGForge.repository.UserRoleRepository;
import com.mgforge.MGForge.security.SecurityUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    public List<UserEntity> getUsersByTenant(UUID tenantId){
        return userRepository.findAllByTenantId(tenantId);
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN','SUPERADMIN')")
    public List<UserEntity> tenantUsers(){
        var principal = SecurityUtils.currentPrincipal();

        if(principal.hasRole("SUPERADMIN")){
            throw new AccountDisabledException("SuperAdmin should use cross-tenant query");
        }

        return userRepository.findAllByTenantId(principal.getTenantId());
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN','SUPERADMIN')")
    public UserEntity createUser(CreateUserInput input){
        var principal = SecurityUtils.currentPrincipal();

        UserEntity user = new UserEntity();
        user.setTenantId(principal.hasRole("SUPERADMIN") ? null : principal.getTenantId());
        user.setMobile(input.getMobile());
        user.setName(input.getName());
        user.setPasswordHash(passwordEncoder.encode(input.getRawPassword()));
        user.setStatus(UserStatus.ACTIVE);
        // password has should be set separately

        UserEntity saved = userRepository.save(user);

        if (input.getRoles() != null) {
            for (String role : input.getRoles()){
                userRoleRepository.save(new UserRoleEntity(saved.getId(),
                        RoleType.valueOf(role)));
            }
        }

        return saved;
    }

    @PreAuthorize("hasRole('SUPERADMIN') or @rbac.canAccessUser(#userId)")
    public UserEntity updateUser(UUID userId, UpdateUserInput input){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new AccessDeniedException("User not found"));

        if(input.getName() != null){
            user.setName(input.getName());
        }
        /* if(input.getPictureUrl() != null){
            user.setPictureUrl(input.getPictureUrl());
        }*/
        if (input.getStatus() != null){
            user.setStatus(UserStatus.valueOf(input.getStatus()));
        }

        return userRepository.save(user);
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','SUPERADMIN')")
    public UserEntity assignRoles(AssignRoleInput input){
        UserEntity user = userRepository.findById(input.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        userRoleRepository.deleteByUserId(user.getId());

        if(input.getRoles() != null) {
            for (String role : input.getRoles()){
                userRoleRepository.save(new UserRoleEntity(user.getId(), RoleType.valueOf(role)));
            }
        }

        return user;
    }
}
