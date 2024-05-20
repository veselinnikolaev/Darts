package com.example.darts.repository;

import com.example.darts.model.entity.JobApplication;
import com.example.darts.model.entity.Location;
import com.example.darts.model.enumeration.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long>,
        JpaSpecificationExecutor<JobApplication>,
        PagingAndSortingRepository<JobApplication, Long> {
    Page<JobApplication> findAllByCategory(Category category, Pageable pageable);
    Page<JobApplication> findAll(Specification<JobApplication> spec, Pageable pageable);
    List<JobApplication> findTop5ByOrderByPostedDesc();
}
