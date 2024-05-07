package com.example.darts.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "companies")
@Data
public class Company extends BaseEntity{
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "companies")
    private List<Location> location;
    @OneToMany(mappedBy = "companies")
    private List<JobApplication> jobApplications;
}
