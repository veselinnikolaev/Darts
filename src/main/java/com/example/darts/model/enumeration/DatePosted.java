package com.example.darts.model.enumeration;

public enum DatePosted {
    ALL("All", "all"),
    PAST_DAY("Past day", "today"),
    PAST_3_DAYS("Past 3 days", "3days"),
    PAST_WEEK("Past week", "week"),
    PAST_MONTH("Past month", "month");

    private final String label;
    private final String value;

    DatePosted(String label, String value) {
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
