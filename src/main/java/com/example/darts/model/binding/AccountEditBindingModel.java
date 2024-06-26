package com.example.darts.model.binding;

import com.example.darts.model.entity.Location;
import com.example.darts.model.entity.Skill;
import com.example.darts.validation.annotation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class AccountEditBindingModel {
    @NotBlank
    @Size(min = 3, max = 20, message = "First name length must be between 3 and 20 characters!")
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 20, message = "Last name length must be between 3 and 20 characters!")
    private String lastName;
    @Email(message = "Invalid email!")
    @NotBlank(message = "Email cannot be empty!")
    private String email;
    private Location location;
    //@Phone
    private String phone;
    private String about;
    private MultipartFile photo;
    private MultipartFile cv;
    private List<Skill> skills;
}
