package com.example.darts.model.binding;

import com.example.darts.model.entity.Account;
import com.example.darts.model.entity.JobApplication;
import com.example.darts.model.entity.Location;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CompanyBindingModel {
    @NotBlank
    private String name;
    private String description;
    private String website;
    @NotBlank
    private String email;
    private MultipartFile logo;
    @NotNull
    private List<Location> locations;
}
