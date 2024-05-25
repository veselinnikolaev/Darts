package com.example.darts.model.enumeration;

import com.example.darts.model.json.JobApplicationJSON;

public enum ExperienceLevel {
    NO_DEGREE("No degree", "no_degree"),
    NO_EXPERIENCE("No experience", "no_experience"),
    UNDER_3_YEARS_EXPERIENCE("Under 3 years experience", "under_3_years_experience"),
    MORE_THAN_3_YEARS_EXPERIENCE("More than 3 years experience", "more_than_3_years_experience");

    private final String label;
    private final String value;
    ExperienceLevel(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public static ExperienceLevel parse(JobApplicationJSON.JobRequiredExperience jobRequiredExperience){
        if (jobRequiredExperience.isNoExperienceRequired()) {
            return NO_EXPERIENCE;
        }

        if (jobRequiredExperience.isExperienceMentioned() && jobRequiredExperience.getRequiredExperienceInMonths() != null &&
                jobRequiredExperience.getRequiredExperienceInMonths() < 36) {
            return UNDER_3_YEARS_EXPERIENCE;
        }

        return MORE_THAN_3_YEARS_EXPERIENCE;
    }
}
