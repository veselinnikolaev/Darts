package com.example.darts.service;

import com.example.darts.model.binding.JobApplicationBindingModel;
import com.example.darts.model.entity.*;
import com.example.darts.model.enumeration.Category;
import com.example.darts.repository.JobApplicationRepository;
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
    private final LocationService locationService;
    private final CompanyService companyService;
    private final SkillService skillService;
    private final ExperienceService experienceService;

    public Page<JobApplication> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<JobApplication> getAll(String keyword, String location, Pageable pageable) {
        if ((keyword.isEmpty() || keyword.isBlank()) && location.equals("all")) {
            return repository.findAll(pageable);
        }

        Specification<JobApplication> spec = Specification.where(hasKeyword(keyword));

        if (!location.equals("all")) {
            Location locationEntity = locationService.getById(Long.parseLong(location));
            spec = spec.and(hasLocation(locationEntity));
        }

        return repository.findAll(spec, pageable);
    }

    public JobApplication getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job application not found"));
    }

    public Page<JobApplication> getAllByCategory(Category category, Pageable pageable) {
        return repository.findAllByCategory(category, pageable);
    }

    public List<JobApplication> getAllByCategory(Category category) {
        return repository.findAllByCategory(category);
    }

    public List<JobApplication> getTop5JobApplicationsByDatePosted() {
        return repository.findTop5ByOrderByPostedDesc();
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
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("company").get("description")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("location").get("city")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("location").get("region")), likePattern)
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

    public void save(JobApplicationBindingModel bindingModel, Account account) {
        repository.save(new JobApplication(bindingModel, account));
    }
}
