package com.example.darts.model.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class JobApplicationJSON {
    @SerializedName("employer_name")
    private String employerName;

    @SerializedName("employer_logo")
    private String employerLogo;

    @SerializedName("employer_website")
    private String employerWebsite;

    @SerializedName("employer_company_type")
    private String employerCompanyType;

    @SerializedName("job_publisher")
    private String jobPublisher;

    @SerializedName("job_id")
    private String jobId;

    @SerializedName("job_employment_type")
    private String jobEmploymentType;

    @SerializedName("job_title")
    private String jobTitle;

    @SerializedName("job_apply_link")
    private String jobApplyLink;

    @SerializedName("job_apply_is_direct")
    private boolean jobApplyIsDirect;

    @SerializedName("job_apply_quality_score")
    private double jobApplyQualityScore;

    @SerializedName("job_description")
    private String jobDescription;

    @SerializedName("job_is_remote")
    private boolean jobIsRemote;

    @SerializedName("job_posted_at_timestamp")
    private long jobPostedAtTimestamp;

    @SerializedName("job_posted_at_datetime_utc")
    private String jobPostedAtDatetimeUtc;

    @SerializedName("job_city")
    private String jobCity;

    @SerializedName("job_state")
    private String jobState;

    @SerializedName("job_country")
    private String jobCountry;

    @SerializedName("job_latitude")
    private double jobLatitude;

    @SerializedName("job_longitude")
    private double jobLongitude;

    @SerializedName("job_benefits")
    private List<String> jobBenefits;

    @SerializedName("job_google_link")
    private String jobGoogleLink;

    @SerializedName("job_offer_expiration_datetime_utc")
    private String jobOfferExpirationDatetimeUtc;

    @SerializedName("job_offer_expiration_timestamp")
    private long jobOfferExpirationTimestamp;

    @SerializedName("job_required_experience")
    private JobRequiredExperience jobRequiredExperience;

    @SerializedName("job_required_skills")
    private List<String> jobRequiredSkills;

    @SerializedName("job_required_education")
    private JobRequiredEducation jobRequiredEducation;

    @SerializedName("job_min_salary")
    private Double jobMinSalary;

    @SerializedName("job_max_salary")
    private Double jobMaxSalary;

    @SerializedName("job_salary_currency")
    private String jobSalaryCurrency;

    @SerializedName("job_salary_period")
    private String jobSalaryPeriod;

    @SerializedName("job_highlights")
    private JobHighlights jobHighlights;

    @SerializedName("job_job_title")
    private String jobJobTitle;

    @SerializedName("job_posting_language")
    private String jobPostingLanguage;

    @SerializedName("job_onet_soc")
    private String jobOnetSoc;

    @SerializedName("job_onet_job_zone")
    private String jobOnetJobZone;

    @SerializedName("job_naics_code")
    private String jobNaicsCode;

    @SerializedName("job_naics_name")
    private String jobNaicsName;

    // Getters and setters for all fields

    @Data
    public static class JobRequiredExperience {
        @SerializedName("no_experience_required")
        private boolean noExperienceRequired;

        @SerializedName("required_experience_in_months")
        private Integer requiredExperienceInMonths;

        @SerializedName("experience_mentioned")
        private boolean experienceMentioned;

        @SerializedName("experience_preferred")
        private boolean experiencePreferred;

        // Getters and setters
    }

    @Data
    public static class JobRequiredEducation {
        @SerializedName("postgraduate_degree")
        private boolean postgraduateDegree;

        @SerializedName("professional_certification")
        private boolean professionalCertification;

        @SerializedName("high_school")
        private boolean highSchool;

        @SerializedName("associates_degree")
        private boolean associatesDegree;

        @SerializedName("bachelors_degree")
        private boolean bachelorsDegree;

        @SerializedName("degree_mentioned")
        private boolean degreeMentioned;

        @SerializedName("degree_preferred")
        private boolean degreePreferred;

        @SerializedName("professional_certification_mentioned")
        private boolean professionalCertificationMentioned;

        @SerializedName("job_experience_in_place_of_education")
        private boolean jobExperienceInPlaceOfEducation;

        // Getters and setters
    }

    @Data
    public static class JobHighlights {
        @SerializedName("Qualifications")
        private List<String> qualifications;

        @SerializedName("Responsibilities")
        private List<String> responsibilities;

        @SerializedName("Benefits")
        private List<String> benefits;

        // Getters and setters
    }
}
