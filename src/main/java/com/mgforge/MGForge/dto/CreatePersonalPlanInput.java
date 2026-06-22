package com.mgforge.MGForge.dto;

import java.time.LocalDate;

public class CreatePersonalPlanInput {

    private String clientId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Object program;

    public CreatePersonalPlanInput() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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
