package com.mgforge.MGForge.document;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "planDefinition")
public class PlanDefinitionDocument  extends BaseMongoAuditDocument {

    @Id
    private String id;

    private String tenantId;
    private SourceInfo source;
    private String name;
    private List<WeekBlock> weeks;

    public PlanDefinitionDocument() {
    }

    public PlanDefinitionDocument(String id, String tenantId, SourceInfo source, String name, List<WeekBlock> weeks) {
        this.id = id;
        this.tenantId = tenantId;
        this.source = source;
        this.name = name;
        this.weeks = weeks;
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

    public SourceInfo getSource() {
        return source;
    }

    public void setSource(SourceInfo source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WeekBlock> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<WeekBlock> weeks) {
        this.weeks = weeks;
    }

    public static class SourceInfo{
        private String type;
        private String templateId;
        private Integer templateVersion;

        public SourceInfo() {
        }

        public SourceInfo(String type, String templateId, Integer templateVersion) {
            this.type = type;
            this.templateId = templateId;
            this.templateVersion = templateVersion;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public Integer getTemplateVersion() {
            return templateVersion;
        }

        public void setTemplateVersion(Integer templateVersion) {
            this.templateVersion = templateVersion;
        }
    }

    public static class WeekBlock {
        private Integer weekNumber;
        private List<DayBlock> days;

        public WeekBlock() {
        }

        public WeekBlock(Integer weekNumber, List<DayBlock> days) {
            this.weekNumber = weekNumber;
            this.days = days;
        }

        public Integer getWeekNumber() {
            return weekNumber;
        }

        public void setWeekNumber(Integer weekNumber) {
            this.weekNumber = weekNumber;
        }

        public List<DayBlock> getDays() {
            return days;
        }

        public void setDays(List<DayBlock> days) {
            this.days = days;
        }
    }

    public static class DayBlock{
        private Integer dayNumber;
        private String title;
        private List<ExerciseBlock> exercises;

        public DayBlock() {
        }

        public DayBlock(Integer dayNumber, String title, List<ExerciseBlock> exercises) {
            this.dayNumber = dayNumber;
            this.title = title;
            this.exercises = exercises;
        }

        public Integer getDayNumber() {
            return dayNumber;
        }

        public void setDayNumber(Integer dayNumber) {
            this.dayNumber = dayNumber;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ExerciseBlock> getExercises() {
            return exercises;
        }

        public void setExercises(List<ExerciseBlock> exercises) {
            this.exercises = exercises;
        }
    }

    public static class ExerciseBlock {
        private String exerciseId;
        private Integer order;
        private String notes;
        private List<SetTarget> sets;

        public ExerciseBlock() {
        }

        public ExerciseBlock(String exerciseId, Integer order, String notes, List<SetTarget> sets) {
            this.exerciseId = exerciseId;
            this.order = order;
            this.notes = notes;
            this.sets = sets;
        }

        public String getExerciseId() {
            return exerciseId;
        }

        public void setExerciseId(String exerciseId) {
            this.exerciseId = exerciseId;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public List<SetTarget> getSets() {
            return sets;
        }

        public void setSets(List<SetTarget> sets) {
            this.sets = sets;
        }
    }

    public static class SetTarget {
        private Integer setNumber;
        private Integer targetReps;
        private Double targetWeight;
        private String unit;
        private Integer restSec;

        public SetTarget() {
        }

        public SetTarget(Integer setNumber, Integer targetReps, Double targetWeight, String unit, Integer restSec) {
            this.setNumber = setNumber;
            this.targetReps = targetReps;
            this.targetWeight = targetWeight;
            this.unit = unit;
            this.restSec = restSec;
        }

        public Integer getSetNumber() {
            return setNumber;
        }

        public void setSetNumber(Integer setNumber) {
            this.setNumber = setNumber;
        }

        public Integer getTargetReps() {
            return targetReps;
        }

        public void setTargetReps(Integer targetReps) {
            this.targetReps = targetReps;
        }

        public Double getTargetWeight() {
            return targetWeight;
        }

        public void setTargetWeight(Double targetWeight) {
            this.targetWeight = targetWeight;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public Integer getRestSec() {
            return restSec;
        }

        public void setRestSec(Integer restSec) {
            this.restSec = restSec;
        }
    }
}
