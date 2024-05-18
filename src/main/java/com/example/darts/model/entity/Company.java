package com.example.darts.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "companies")
@Data
public class Company extends BaseEntity{
    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String website;
    private String email;
    private String logo;
    @ManyToMany(mappedBy = "companies")
    private List<Location> locations;
    @OneToMany(mappedBy = "company")
    private List<JobApplication> jobApplications;
    @ManyToMany
    private List<Account> accounts;
}
