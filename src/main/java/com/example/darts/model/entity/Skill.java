package com.example.darts.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
@Table(name = "skills")
@Data
public class Skill extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    @Range(min = 1, max = 100)
    private Integer percentage;
    @ManyToMany
    private List<Account> accounts;
    @ManyToMany
    private List<JobApplication> jobApplications;
}
