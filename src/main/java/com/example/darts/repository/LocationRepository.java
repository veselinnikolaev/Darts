package com.example.darts.repository;

import com.example.darts.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findFirstByCity(String city);

    boolean existsByCity(String city);
}
