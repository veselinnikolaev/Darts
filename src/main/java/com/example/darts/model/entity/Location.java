package com.example.darts.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "locations")
@Data
public class Location extends BaseEntity{
    @Column(unique = true)
    private String name;
    private Double latitude;
    private Double longitude;
    @ManyToMany
    private List<Company> companies;
    @OneToMany(mappedBy = "location")
    private List<JobApplication> jobApplications;
}
