package com.mgforge.MGForge.dto;

import java.util.List;

public class CreateExerciseInput {

    private String name;
    private String description;
    private String primaryMuscle;
    private String equipment;
    private String difficulty;
    private List<CreateExerciseMediaInput> media;

    public CreateExerciseInput() {
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

    public List<CreateExerciseMediaInput> getMedia() {
        return media;
    }

    public void setMedia(List<CreateExerciseMediaInput> media) {
        this.media = media;
    }
}
