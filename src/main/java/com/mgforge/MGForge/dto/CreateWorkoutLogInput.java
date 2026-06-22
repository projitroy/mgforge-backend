package com.mgforge.MGForge.dto;

import java.time.OffsetDateTime;

public class CreateWorkoutLogInput {

    private String planInstanceId;
    private Integer weekNumber;
    private Integer dayNumber;
    private OffsetDateTime completedAt;
    private Object entries;
    private String clientNotes;

    public CreateWorkoutLogInput() {
    }

    public String getPlanInstanceId() {
        return planInstanceId;
    }

    public void setPlanInstanceId(String planInstanceId) {
        this.planInstanceId = planInstanceId;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public OffsetDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(OffsetDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Object getEntries() {
        return entries;
    }

    public void setEntries(Object entries) {
        this.entries = entries;
    }

    public String getClientNotes() {
        return clientNotes;
    }

    public void setClientNotes(String clientNotes) {
        this.clientNotes = clientNotes;
    }
}
