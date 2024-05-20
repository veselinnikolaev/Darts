package com.example.darts.init;

import com.example.darts.model.entity.Skill;
import com.example.darts.model.json.SkillJSON;
import com.example.darts.repository.SkillRepository;
import com.google.gson.Gson;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SkillInit implements CommandLineRunner {
    private final SkillRepository repository;
    private final Gson gson;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() != 0) {
            return;
        }
        try (FileReader reader = new FileReader("skills.json")) {
            List<Skill> skills = Arrays.stream(gson.fromJson(reader, SkillJSON[].class)).map(Skill::new).toList();

            repository.saveAll(skills);
        }
    }
}