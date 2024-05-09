package com.example.darts.init;

import com.example.darts.model.json.LocationJSON;
import com.example.darts.model.entity.Location;
import com.example.darts.repository.LocationRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class LocationInit implements CommandLineRunner {
    private final Gson gson;
    private final LocationRepository repository;
    @Override
    public void run(String... args) throws Exception {
        if(repository.count() == 0) {
            try(FileReader reader = new FileReader("locations.json")) {
                List<Location> locations = Arrays.stream(gson.fromJson(reader, LocationJSON[].class))
                        .map(Location::new)
                        .toList();

                repository.saveAll(locations);
            }
        }
    }
}
