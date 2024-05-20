package com.example.darts.model.entity;

import com.example.darts.model.json.ExperienceJSON;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "experiences")
@Data
@NoArgsConstructor
public class Experience extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @ManyToMany
    private List<Account> accounts;
    @ManyToMany
    private List<JobApplication> jobApplications;

    public Experience(ExperienceJSON json){
        this.name = json.getName();
        this.description = json.getDescription();
    }
}
