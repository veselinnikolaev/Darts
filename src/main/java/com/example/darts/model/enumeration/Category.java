package com.example.darts.model.enumeration;

public enum Category {
    FINANCE("Finance", "L2J1c2luZXNzL25haWNzMjAwNy81MjpGaW5hbmNl"),
    INFORMATION("Information", "L2J1c2luZXNzL25haWNzMjAwNy81MTpJbmZvcm1hdGlvbg=="),
    CONSULTING("Consulting", "L2J1c2luZXNzL25haWNzMjAwNy81NDE2OkNvbnN1bHRpbmc="),
    COMPUTER_SERVICES("Computer Services", "L2J1c2luZXNzL25haWNzMjAwNy81NDE1OkNvbXB1dGVyIFNlcnZpY2Vz"),
    RETAIL("Retail", "L2J1c2luZXNzL25haWNzMjAwNy80NDpSZXRhaWw="),
    STAFFING("Staffing", "L2J1c2luZXNzL25haWNzMjAwNy81NjEzOlN0YWZmaW5n"),
    HEALTH_CARE("Health Care", "L2J1c2luZXNzL25haWNzMjAwNy82MjpIZWFsdGggQ2FyZQ=="),
    MANUFACTURING("Manufacturing", "L2J1c2luZXNzL25haWNzMjAwNy8zMTpNYW51ZmFjdHVyaW5n");

    private final String label;
    private final String value;
    Category(String label, String value) {
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
