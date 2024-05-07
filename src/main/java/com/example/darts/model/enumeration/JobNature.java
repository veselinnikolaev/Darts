package com.example.darts.model.enumeration;

public enum JobNature {
    FULL_TIME("Full time"),
    PART_TIME("Part time"),
    REMOTE("Remote"),
    FREELANCE("Freelance");

    public final String label;
    JobNature(String label) {
        this.label = label;
    }
}
