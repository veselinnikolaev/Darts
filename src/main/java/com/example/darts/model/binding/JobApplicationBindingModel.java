package com.example.darts.model.binding;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class JobApplicationBindingModel {
    @NotBlank
    private String position;
    private Long company;
    private Long location;
    @PositiveOrZero
    private Integer vacancy;
    @Positive
    private Double salary;
    private String jobNature;
    @Future
    private String due;
    private List<Long> requiredSkills;
    private List<Long> requiredExperiences;
    private String description;
    private String category;
}
