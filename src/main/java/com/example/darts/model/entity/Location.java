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
    @Column(unique = true)
    private String city;
    private Double latitude;
    private Double longitude;
    private String region;
    @ManyToMany
    private List<Company> companies;
    @OneToMany(mappedBy = "location")
    private List<JobApplication> jobApplications;

    public Location(LocationJSON location){
        this.city = location.getCity();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.region = location.getRegion();
    }
}
