package com.example.darts.model.entity;

import com.example.darts.model.json.SkillJSON;
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
@NoArgsConstructor
public class Skill extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany
    private List<Account> accounts;
    @ManyToMany
    private List<JobApplication> jobApplications;

    public Skill(SkillJSON jSON) {
        this.name = jSON.getName();
    }
}
