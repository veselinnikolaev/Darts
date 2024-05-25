package com.example.darts.model.enumeration;

public enum EmploymentType {
    FULL_TIME("Full-time", "FULLTIME"),
    CONTRACTOR("Contractor", "CONTRACTOR"),
    PART_TIME("Part-time", "PARTTIME"),
    INTERNSHIP("Internship", "INTERN");

    private final String label;
    private final String value;
    EmploymentType(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
