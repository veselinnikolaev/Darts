package com.example.darts.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "locations")
@Data
public class Location extends BaseEntity{
    @Column(unique = true)
    private String name;
    private Double latitude;
    private Double longitude;
    @ManyToMany(mappedBy = "locations")
    private List<Company> companies;
}
