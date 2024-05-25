package com.example.darts.model.entity;

import com.example.darts.model.json.LocationJSON;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
public class Location extends BaseEntity{
    private String city;
    private Double latitude;
    private Double longitude;
    private String region;
    private String country;
    @ManyToMany
    private List<Company> companies;
    @OneToMany(mappedBy = "location")
    private List<JobApplication> jobApplications;
    @OneToMany(mappedBy = "location")
    private List<Account> accounts;

    public Location(LocationJSON location){
        this.city = location.getCity();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.region = location.getRegion();
    }

    public Location(String city, Double latitude, Double longitude, String country){
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }
}
