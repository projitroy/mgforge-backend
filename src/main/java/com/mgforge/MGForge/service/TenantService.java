package com.mgforge.MGForge.service;

import com.mgforge.MGForge.entity.TenantEntity;
import com.mgforge.MGForge.exception.ResourceNotFoundException;
import com.mgforge.MGForge.repository.TenantRepository;
import com.mgforge.MGForge.security.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @PreAuthorize("hasRole('SUPERADMIN'")
    public List<TenantEntity> tenants(){
        return tenantRepository.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    public TenantEntity currentTenant(){
        UUID tenantId = SecurityUtils.currentPrincipal().getTenantId();
        if(tenantId == null){
            throw new ResourceNotFoundException("Current tenant not available");
        }

        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
    }
}
