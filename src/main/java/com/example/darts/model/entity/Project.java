package com.example.darts.model.entity;

import com.example.darts.model.enumeration.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
public class Project extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDate posted;
    private String photo;
    @ManyToOne
    private Account account;
    @ManyToMany
    private List<Skill> skills;
    @Enumerated(EnumType.STRING)
    private Category category;
}
