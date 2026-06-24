package com.mgforge.MGForge.entity;

import com.mgforge.MGForge.enums.RoleType;

import java.util.Objects;
import java.util.UUID;

public class UserRoleId {

    private UUID userId;
    private RoleType roleId;

    public UserRoleId(){}

    public UserRoleId(UUID userId, RoleType roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public RoleType getRoleId() {
        return roleId;
    }

    public void setRoleId(RoleType roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}
