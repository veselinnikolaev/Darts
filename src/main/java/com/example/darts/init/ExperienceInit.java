package com.example.darts.init;

import com.example.darts.model.entity.Experience;
import com.example.darts.model.json.ExperienceJSON;
import com.example.darts.repository.ExperienceRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExperienceInit implements CommandLineRunner {
    private final ExperienceRepository repository;
    private final Gson gson;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() != 0) {
            return;
        }
        try (FileReader reader = new FileReader("experiences.json")) {
            List<Experience> experiences = Arrays.stream(gson.fromJson(reader, ExperienceJSON[].class)).map(Experience::new).toList();

            repository.saveAll(experiences);
        }
    }
}
