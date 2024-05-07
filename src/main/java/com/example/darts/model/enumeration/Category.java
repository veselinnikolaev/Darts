package com.example.darts.model.enumeration;

public enum Category {
    DESIGN_CREATIVE("Design & Creative"),
    DESIGN_DEVELOPMENT("Design & Development"),
    SALES_MARKETING("Sales & Marketing"),
    MOBILE_APPLICATION("Mobile Application"),
    CONSTRUCTION("Construction"),
    INFORMATION_TECHNOLOGY("Information Technology"),
    REAL_ESTATE("Real Estate"),
    CONTENT_WRITER("Content Writer");

    public final String label;
    Category(String label) {
        this.label = label;
    }
}
