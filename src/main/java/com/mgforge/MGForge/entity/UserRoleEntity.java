package com.mgforge.MGForge.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name ="user_roles")
@IdClass(UserRoleId.class)
public class UserRoleEntity {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "role_code")
    private String roleId;
}
