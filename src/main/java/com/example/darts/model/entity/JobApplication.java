package com.example.darts.model.entity;

import com.example.darts.model.binding.JobApplicationBindingModel;
import com.example.darts.model.enumeration.Category;
import com.example.darts.model.enumeration.ExperienceLevel;
import com.example.darts.model.enumeration.EmploymentType;
import com.example.darts.model.json.JobApplicationJSON;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "job_applications")
@Data
@NoArgsConstructor
public class JobApplication extends BaseEntity {
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT")
    private EmploymentType employmentType;
    @ManyToOne
    private Company company;
    @ManyToOne
    private Location location;
    private Integer vacancy;
    @Positive
    private BigDecimal salary;
    private LocalDate posted;
    private LocalDate due;
    @ManyToMany(mappedBy = "jobApplications")
    private List<Skill> skills;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT")
    private ExperienceLevel experienceLevel;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT")
    private Category category;
    @ManyToMany(mappedBy = "jobApplications")
    private List<Account> applicants;
    @Transient
    private String link;

    public JobApplication(JobApplicationBindingModel bindingModel) {
        this.title = bindingModel.getPosition();
        this.company = bindingModel.getCompany();
        this.location = bindingModel.getLocation();
        this.vacancy = bindingModel.getVacancy();
        this.salary = BigDecimal.valueOf(bindingModel.getSalary());
        this.employmentType = bindingModel.getEmploymentType();
        this.posted = LocalDate.now();
        this.due = LocalDate.parse(bindingModel.getDue());
        this.skills = bindingModel.getSkills();
        this.experienceLevel = bindingModel.getExperienceLevel();
        this.description = bindingModel.getDescription();
        this.category = bindingModel.getCategory();
    }

    public JobApplication(JobApplicationJSON jobApplicationJSON, Location location) {
        this.title = jobApplicationJSON.getJobTitle();
        this.employmentType = Arrays.stream(EmploymentType.values())
                .filter(et -> et.getValue().equals(jobApplicationJSON.getJobEmploymentType()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Employment type not found"));
        this.company = new Company();
        this.company.setName(jobApplicationJSON.getEmployerName());
        this.company.setLogo(jobApplicationJSON.getEmployerLogo());
        this.company.setWebsite(jobApplicationJSON.getEmployerWebsite());
        this.company.setDescription(jobApplicationJSON.getEmployerCompanyType());
        this.location = location;
        this.vacancy = new Random().nextInt(1, 23); // Assuming default value for vacancy
        if (jobApplicationJSON.getJobMinSalary() != null && jobApplicationJSON.getJobMaxSalary() != null) {
            double averageSalary = (jobApplicationJSON.getJobMinSalary() + jobApplicationJSON.getJobMaxSalary()) / 2;
            this.salary = BigDecimal.valueOf(averageSalary);
        }
        this.posted = Instant.ofEpochSecond(jobApplicationJSON.getJobPostedAtTimestamp()).atZone(ZoneId.of("UTC")).toLocalDate();
        this.due = Instant.ofEpochSecond(jobApplicationJSON.getJobOfferExpirationTimestamp()).atZone(ZoneId.of("UTC")).toLocalDate();
        if (jobApplicationJSON.getJobRequiredSkills() != null) {
            this.skills = createSkills(jobApplicationJSON.getJobRequiredSkills());
        }
        this.experienceLevel = ExperienceLevel.parse(jobApplicationJSON.getJobRequiredExperience());
        this.description = jobApplicationJSON.getJobDescription();
        this.category = Category.valueOf(jobApplicationJSON.getEmployerCompanyType().toUpperCase().replace(" ", "_"));
        this.applicants = new ArrayList<>(); // Assuming empty list for applicants
        this.link = jobApplicationJSON.getJobApplyLink() == null ? jobApplicationJSON.getJobGoogleLink() : jobApplicationJSON.getJobApplyLink();
    }

    private List<Skill> createSkills(List<String> skillNames) {
        List<Skill> skills = new ArrayList<>();
        for (String skillName : skillNames) {
            Skill skill = new Skill();
            skill.setName(skillName);
            // You can set other properties of Skill if needed
            skills.add(skill);
        }
        return skills;
    }
}
