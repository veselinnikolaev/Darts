package com.example.darts.service;

import com.example.darts.model.entity.JobApplication;
import com.example.darts.model.entity.Location;
import com.example.darts.model.enumeration.Category;
import com.example.darts.repository.JobApplicationRepository;
import com.example.darts.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final JobApplicationRepository repository;
    private final LocationRepository locationRepository;

    public Page<JobApplication> getAllJobApplications(String keyword, String location, Pageable pageable) {
        if ("all".equals(keyword) && "all".equals(location)) {
            return repository.findAll(pageable);
        }

        Specification<JobApplication> spec = Specification.where(hasKeyword(keyword));

        if (!"all".equals(location)) {
            Location locationEntity = locationRepository.findById(Long.parseLong(location))
                    .orElseThrow(() -> new RuntimeException("Location not found"));
            spec = spec.and(hasLocation(locationEntity));
        }

        return repository.findAll(spec, pageable);
    }

    public JobApplication getJobApplicationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job application not found"));
    }

    public Page<JobApplication> getJobApplicationsByCategory(Category category, Pageable pageable) {
        return repository.findAllByCategory(category, pageable);
    }

    public Page<JobApplication> getTop5JobApplicationsByDatePosted(Pageable pageable) {
        return repository.findTop5ByOrderByPostedDesc(pageable);
    }

    public String calculateTimeAgo(LocalDate postedDate) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime postedDateTime = postedDate.atStartOfDay();

        long daysBetween = ChronoUnit.DAYS.between(postedDateTime, now);
        if (daysBetween > 0) {
            return daysBetween + " days ago";
        }

        long hoursBetween = ChronoUnit.HOURS.between(postedDateTime, now);
        if (hoursBetween > 0) {
            return hoursBetween + " hours ago";
        }

        long minutesBetween = ChronoUnit.MINUTES.between(postedDateTime, now);
        if (minutesBetween > 0) {
            return minutesBetween + " minutes ago";
        }

        return "just now";
    }

    private Specification<JobApplication> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            String likePattern = "%" + keyword.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("position")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("company").get("name")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("location").get("name")), likePattern)
            );
        };
    }

    private Specification<JobApplication> hasLocation(Location location) {
        return (root, query, criteriaBuilder) -> {
            if (location == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("location"), location);
        };
    }
}
