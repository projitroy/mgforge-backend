package com.mgforge.MGForge.entity;

import com.mgforge.MGForge.enums.RoleType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name ="user_roles")
@IdClass(UserRoleId.class)
public class UserRoleEntity {

    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "role_code", nullable = false, length = 50, updatable = false)
    private RoleType roleId;

    public UserRoleEntity() {
    }

    public UserRoleEntity(UUID userId, RoleType roleId) {
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
}
