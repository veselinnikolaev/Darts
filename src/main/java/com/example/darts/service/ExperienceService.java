package com.example.darts.service;

import com.example.darts.model.entity.Experience;
import com.example.darts.repository.ExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {
    private final ExperienceRepository repository;
    public Experience getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found"));
    }

    public List<Experience> getAll() {
        return repository.findAll();
    }
}
