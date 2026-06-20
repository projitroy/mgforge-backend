package com.mgforge.MGForge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(
        name = "coach_client_links",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_coach_client", columnNames = {"tenant_id","coach_id","client_id"})
        }
)
public class CoachClientEntity extends BaseAuditEntity{

    @Id
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "coach_id", nullable = false)
    private UUID coachId;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(nullable = false)
    private boolean active;

    public CoachClientEntity() {
    }

    public CoachClientEntity(UUID id, UUID tenantId, UUID coachId, UUID clientId, boolean active) {
        this.id = id;
        this.tenantId = tenantId;
        this.coachId = coachId;
        this.clientId = clientId;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getCoachId() {
        return coachId;
    }

    public void setCoachId(UUID coachId) {
        this.coachId = coachId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
