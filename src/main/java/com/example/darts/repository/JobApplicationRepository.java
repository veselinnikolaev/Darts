package com.example.darts.repository;

import com.example.darts.model.entity.JobApplication;
import com.example.darts.model.entity.Location;
import com.example.darts.model.enumeration.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long>, JpaSpecificationExecutor<JobApplication> {
    List<JobApplication> findAllByCategory(Category category);

    List<JobApplication> findTop5ByOrderByPostedDesc();

    List<JobApplication> findAllByLocation(Location location);
}
