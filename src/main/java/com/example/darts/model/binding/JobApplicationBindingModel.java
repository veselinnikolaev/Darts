package com.example.darts.model.binding;

import com.example.darts.model.entity.Company;
import com.example.darts.model.entity.Experience;
import com.example.darts.model.entity.Location;
import com.example.darts.model.entity.Skill;
import com.example.darts.model.enumeration.Category;
import com.example.darts.model.enumeration.JobNature;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class JobApplicationBindingModel {
    @NotBlank
    private String position;
    @NotNull
    private Company company;
    @NotNull
    private Location location;
    @PositiveOrZero
    @NotNull
    private Integer vacancy;
    @NotNull
    @Positive
    private Double salary;
    @NotNull
    private JobNature jobNature;
    @Future
    private String due;
    @NotNull
    private List<Skill> requiredSkills;
    @NotNull
    private List<Experience> requiredExperiences;
    private String description;
    @NotNull
    private Category category;
}
