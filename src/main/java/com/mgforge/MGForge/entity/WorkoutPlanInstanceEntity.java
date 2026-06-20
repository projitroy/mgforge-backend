package com.mgforge.MGForge.entity;

import com.mgforge.MGForge.enums.AssignmentStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "workout_plan_instances")
public class WorkoutPlanInstanceEntity extends BaseAuditEntity{

    @Id
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "assinged_by_id", nullable = false)
    private UUID assignedById;

    @Column(name = "template_id", nullable = false)
    private UUID templateId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AssignmentStatus status;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "mongo_defination_id",nullable = false, length = 100)
    private String mongoDefinitionId;

    public WorkoutPlanInstanceEntity() {
    }

    public WorkoutPlanInstanceEntity(UUID id, UUID tenantId, UUID clientId, UUID assignedById, UUID templateId, AssignmentStatus status, LocalDate startDate, LocalDate endDate, String mongoDefinitionId) {
        this.id = id;
        this.tenantId = tenantId;
        this.clientId = clientId;
        this.assignedById = assignedById;
        this.templateId = templateId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getAssignedById() {
        return assignedById;
    }

    public void setAssignedById(UUID assignedById) {
        this.assignedById = assignedById;
    }

    public UUID getTemplateId() {
        return templateId;
    }

    public void setTemplateId(UUID templateId) {
        this.templateId = templateId;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getMongoDefinitionId() {
        return mongoDefinitionId;
    }

    public void setMongoDefinitionId(String mongoDefinitionId) {
        this.mongoDefinitionId = mongoDefinitionId;
    }
}
