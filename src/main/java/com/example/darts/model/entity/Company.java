package com.example.darts.model.entity;

import com.example.darts.model.binding.CompanyBindingModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
public class Company extends BaseEntity{
    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String website;
    private String email;
    private String logo;
    @ManyToMany(mappedBy = "companies")
    private List<Location> locations;
    @OneToMany(mappedBy = "company")
    private List<JobApplication> jobApplications;
    @ManyToMany
    private List<Account> accounts;

    public Company(Account account){
        this.name = account.getFirstName() + " " + account.getLastName();
        this.description = account.getAbout();
        this.email = account.getEmail();
        this.logo = account.getPhoto();
        this.locations = List.of(account.getLocation() == null ? new Location() : account.getLocation());
        this.accounts = List.of(account);
    }

    public Company(CompanyBindingModel bindingModel, List<Location> locations){
        this.name = bindingModel.getName();
        this.description = bindingModel.getDescription();
        this.website = bindingModel.getWebsite();
        this.email = bindingModel.getEmail();
        //this.logo = bindingModel.getLogo();
        this.locations = locations;
    }
}
