package com.example.darts.model.entity;

import com.example.darts.model.enumeration.Category;
import com.example.darts.model.enumeration.JobNature;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "job_applications")
@Data
public class JobApplication extends BaseEntity{
    private String name;
    @ManyToOne
    private Company company;
    @OneToOne
    private Location location;
    private Integer vacancy;
    @Positive
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private JobNature jobNature;
    private LocalDate posted;
    private LocalDate due;
    @OneToMany
    private List<Skill> requiredSkills;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;
}
