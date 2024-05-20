package com.example.darts.service;

import com.example.darts.model.entity.Location;
import com.example.darts.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository repository;

    public Location getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));
    }

    public List<Location> getAll() {
        return repository.findAll();
    }
}
