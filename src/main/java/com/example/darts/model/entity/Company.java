package com.example.darts.model.entity;

import com.example.darts.model.binding.CompanyBindingModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
public class Company extends BaseEntity{
    @Column(unique = true)
    private String name;
    private String logo;
    private String website;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String email;
    @ManyToMany(mappedBy = "companies")
    private List<Location> locations;
    @OneToMany(mappedBy = "company")
    private List<JobApplication> jobApplications;
    @ManyToMany
    private List<Account> accounts;

    public Company(Account account){
        this.name = "%s %s".formatted(account.getFirstName(), account.getLastName());
        this.description = account.getAbout();
        this.email = account.getEmail();
        this.logo = account.getPhoto();
        this.locations = new ArrayList<>(List.of(account.getLocation()));
        this.accounts = new ArrayList<>(List.of(account));
    }

    public Company(CompanyBindingModel bindingModel, String logoUrl){
        this.name = bindingModel.getName();
        this.description = bindingModel.getDescription();
        this.website = bindingModel.getWebsite();
        this.email = bindingModel.getEmail();
        this.logo = logoUrl;
        this.locations = bindingModel.getLocations();
    }
}
