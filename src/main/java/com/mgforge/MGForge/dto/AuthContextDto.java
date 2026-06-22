package com.mgforge.MGForge.dto;

import java.util.List;

public class AuthContextDto {

    private String tenantId;
    private List<String> roles;

    public AuthContextDto() {
    }

    public AuthContextDto(String tenantId, List<String> roles) {
        this.tenantId = tenantId;
        this.roles = roles;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
