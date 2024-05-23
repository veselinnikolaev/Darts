package com.example.darts.model.binding;

import com.example.darts.model.entity.Skill;
import com.example.darts.model.enumeration.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProjectCreateBindingModel {
    private String name;
    private String description;
    private MultipartFile photo;
    private List<Skill> skills;
    private Category category;
}
