package com.example.darts.service;

import com.example.darts.model.entity.Skill;
import com.example.darts.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository repository;

    public Skill getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
    }

    public List<Skill> getAll() {
        return repository.findAll();
    }
}
