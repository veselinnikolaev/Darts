package com.example.darts.model.enumeration;

public enum ExperienceLevel {
    INTERNSHIP("Internship"),
    JUNIOR("Junior"),
    MIDDLE("Middle"),
    SENIOR("Senior");

    public final String label;
    ExperienceLevel(String label) {
        this.label = label;
    }
}
