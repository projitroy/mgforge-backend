package com.mgforge.MGForge.entity;

import com.mgforge.MGForge.enums.PlanStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "workout_plan_templates",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_template_name_version", columnNames = {"tenant_id","name","version"})
        }
)
public class WorkoutPlanTemplateEntity extends BaseAuditEntity {

    @Id
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(nullable = false)
    private Integer version;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PlanStatus status;

    @Column(name = "mongo_defination_id",nullable = false, length = 100)
    private String mongoDefinitionId;

    public WorkoutPlanTemplateEntity() {
    }

    public WorkoutPlanTemplateEntity(UUID id, UUID tenantId, String name, String description, UUID createdBy, Integer version, PlanStatus status, String mongoDefinitionId) {
        this.id = id;
        this.tenantId = tenantId;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.version = version;
        this.status = status;
        this.mongoDefinitionId = mongoDefinitionId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public PlanStatus getStatus() {
        return status;
    }

    public void setStatus(PlanStatus status) {
        this.status = status;
    }

    public String getMongoDefinitionId() {
        return mongoDefinitionId;
    }

    public void setMongoDefinitionId(String mongoDefinitionId) {
        this.mongoDefinitionId = mongoDefinitionId;
    }
}
