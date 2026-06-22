package com.mgforge.MGForge.document;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.List;

@Document(collection = "workoutLogs")
public class WorkoutLogDocument extends BaseMongoAuditDocument {

    @Id
    private String id;

    private String tenantId;
    private String clientId;
    private String coachId;
    private String planInstanceId;

    private Integer weekNumber;
    private Integer dayNumber;

    private OffsetDateTime completedAt;

    private List<WorkoutEntry> entries;

    private String clientNotes;

    private List<String> coachCommentIds;

    public WorkoutLogDocument() {
    }

    public WorkoutLogDocument(String id, String tenantId, String clientId, String coachId, String planInstanceId, Integer weekNumber, Integer dayNumber, OffsetDateTime completedAt, List<WorkoutEntry> entries, String clientNotes, List<String> coachCommentIds) {
        this.id = id;
        this.tenantId = tenantId;
        this.clientId = clientId;
        this.coachId = coachId;
        this.planInstanceId = planInstanceId;
        this.weekNumber = weekNumber;
        this.dayNumber = dayNumber;
        this.completedAt = completedAt;
        this.entries = entries;
        this.clientNotes = clientNotes;
        this.coachCommentIds = coachCommentIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
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

    public List<WorkoutEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<WorkoutEntry> entries) {
        this.entries = entries;
    }

    public String getClientNotes() {
        return clientNotes;
    }

    public void setClientNotes(String clientNotes) {
        this.clientNotes = clientNotes;
    }

    public List<String> getCoachCommentIds() {
        return coachCommentIds;
    }

    public void setCoachCommentIds(List<String> coachCommentIds) {
        this.coachCommentIds = coachCommentIds;
    }

    public static class WorkoutEntry {
        private String exerciseId;
        private List<PerformedSet> performedSets;
        private String notes;

        public WorkoutEntry() {
        }

        public WorkoutEntry(String exerciseId, List<PerformedSet> performedSets, String notes) {
            this.exerciseId = exerciseId;
            this.performedSets = performedSets;
            this.notes = notes;
        }

        public String getExerciseId() {
            return exerciseId;
        }

        public void setExerciseId(String exerciseId) {
            this.exerciseId = exerciseId;
        }

        public List<PerformedSet> getPerformedSets() {
            return performedSets;
        }

        public void setPerformedSets(List<PerformedSet> performedSets) {
            this.performedSets = performedSets;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }

    public static class PerformedSet {
        private Integer setNumber;
        private Integer reps;
        private Double weight;
        private String unit;
        private Double rpe;

        public PerformedSet() {
        }

        public PerformedSet(Integer setNumber, Integer reps, Double weight, String unit, Double rpe) {
            this.setNumber = setNumber;
            this.reps = reps;
            this.weight = weight;
            this.unit = unit;
            this.rpe = rpe;
        }

        public Integer getSetNumber() {
            return setNumber;
        }

        public void setSetNumber(Integer setNumber) {
            this.setNumber = setNumber;
        }

        public Integer getReps() {
            return reps;
        }

        public void setReps(Integer reps) {
            this.reps = reps;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public Double getRpe() {
            return rpe;
        }

        public void setRpe(Double rpe) {
            this.rpe = rpe;
        }
    }
}
