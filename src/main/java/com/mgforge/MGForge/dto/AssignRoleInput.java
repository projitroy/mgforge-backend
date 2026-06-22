package com.mgforge.MGForge.dto;

import java.util.List;
import java.util.UUID;

public class AssignRoleInput {

    private UUID userId;
    private List<String> roles;

    public AssignRoleInput() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
