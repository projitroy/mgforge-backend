package com.mgforge.MGForge.dto;

import com.mgforge.MGForge.enums.AssignmentStatus;

import java.time.LocalDate;

public class UpdateTrainingPlanInput {

    private String title;
    private String description;
    private AssignmentStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Object program;

    public UpdateTrainingPlanInput() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Object getProgram() {
        return program;
    }

    public void setProgram(Object program) {
        this.program = program;
    }
}
