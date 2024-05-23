package com.example.darts.model.entity;

import com.example.darts.model.binding.JobApplicationBindingModel;
import com.example.darts.model.enumeration.Category;
import com.example.darts.model.enumeration.ExperienceLevel;
import com.example.darts.model.enumeration.JobNature;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "job_applications")
@Data
@NoArgsConstructor
public class JobApplication extends BaseEntity {
    private String position;
    @ManyToOne
    private Company company;
    @ManyToOne
    private Location location;
    private Integer vacancy;
    @Positive
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private JobNature jobNature;
    private LocalDate posted;
    private LocalDate due;
    @ManyToMany(mappedBy = "jobApplications")
    private List<Skill> skills;
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToMany(mappedBy = "jobApplications")
    private List<Account> applicants;

    public JobApplication(JobApplicationBindingModel bindingModel, Account account) {
        this.position = bindingModel.getPosition();
        this.company = bindingModel.getCompany();
        this.location = bindingModel.getLocation();
        this.vacancy = bindingModel.getVacancy();
        this.salary = BigDecimal.valueOf(bindingModel.getSalary());
        this.jobNature = bindingModel.getJobNature();
        this.posted = LocalDate.now();
        this.due = LocalDate.parse(bindingModel.getDue());
        this.skills = bindingModel.getSkills();
        this.experienceLevel = bindingModel.getExperienceLevel();
        this.description = bindingModel.getDescription();
        this.category = bindingModel.getCategory();
    }
}
