package com.example.darts.model.entity;

import com.example.darts.model.binding.AccountRegisterBindingModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
public class Account extends BaseEntity {
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, unique = true)
    @Email
    private String email;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String password;
    private String phone;
    @Column(columnDefinition = "TEXT")
    private String about;
    private String CV;
    @ManyToMany(mappedBy = "accounts")
    private List<Experience> experiences;
    @ManyToMany(mappedBy = "accounts")
    private List<Skill> skills;
    @ManyToMany(mappedBy = "accounts")
    private List<Company> companies;
    @ManyToMany
    private List<JobApplication> jobApplications;

    public Account(AccountRegisterBindingModel bindingModel){
        this.firstName = bindingModel.getFirstName();
        this.lastName = bindingModel.getLastName();
        this.email = bindingModel.getEmail();
        this.password = bindingModel.getPassword();
        this.phone = bindingModel.getPhone();
        this.about = bindingModel.getAbout();
    }
}
