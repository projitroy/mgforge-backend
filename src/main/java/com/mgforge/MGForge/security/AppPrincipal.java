package com.mgforge.MGForge.security;

import java.util.List;
import java.util.UUID;

public class AppPrincipal {
    private UUID userId;
    private UUID tenantId;
    private List<String> roles;

    public AppPrincipal() {
    }

    public AppPrincipal(UUID userId, UUID tenantId, List<String> roles) {
        this.userId = userId;
        this.tenantId = tenantId;
        this.roles = roles;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public boolean hasRole(String role){
        return roles != null && roles.contains(role);
    }

    @Override
    public String toString(){
        return "AppPrincipal{"+
                "userId=" + userId +
                ", tenantId=" + tenantId +
                ", roles=" + roles +
                "}";
    }
}
