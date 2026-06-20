package com.mgforge.MGForge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name="exercises",
        uniqueConstraints = {
        @UniqueConstraint(name= "uk_exercise_tenant_name", columnNames = {"tenant_id", "name"})
})
public class ExerciseEntity extends BaseAuditEntity{

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

    @Column(name = "primary_muscle", length = 100)
    private String primaryMuscle;

    @Column(length = 100)
    private String equipment;

    @Column(length = 100)
    private String difficulty;

    @Column(name = "is_active", nullable = false)
    private String isActive;

    public ExerciseEntity() {
    }

    public ExerciseEntity(UUID id, UUID tenantId, String name, String description, String primaryMuscle, String equipment, String difficulty, String isActive) {
        this.id = id;
        this.tenantId = tenantId;
        this.name = name;
        this.description = description;
        this.primaryMuscle = primaryMuscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.isActive = isActive;
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

    public String getPrimaryMuscle() {
        return primaryMuscle;
    }

    public void setPrimaryMuscle(String primaryMuscle) {
        this.primaryMuscle = primaryMuscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
