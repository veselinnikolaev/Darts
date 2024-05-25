package com.example.darts.service;

import com.example.darts.model.binding.JobApplicationBindingModel;
import com.example.darts.model.entity.*;
import com.example.darts.model.enumeration.Category;
import com.example.darts.model.enumeration.DatePosted;
import com.example.darts.model.enumeration.EmploymentType;
import com.example.darts.model.enumeration.ExperienceLevel;
import com.example.darts.model.json.JobApplicationData;
import com.example.darts.model.json.JobApplicationJSON;
import com.example.darts.repository.JobApplicationRepository;
import com.google.gson.Gson;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private static final String API_KEY = "2d0117507cmsh9f965f575c68806p10779fjsnee40ef16a198";
    private static final String API_HOST = "jsearch.p.rapidapi.com";
    private final Gson gson;
    private final JobApplicationRepository repository;
    private final LocationService locationService;
    private final AccountService accountService;

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
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likePattern),
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

    public void apply(JobApplication jobApplication, Account account) {
        List<JobApplication> accountApplications = account.getJobApplications();
        if (accountApplications == null) {
            accountApplications = new ArrayList<>(List.of(jobApplication));
        } else {
            accountApplications.add(jobApplication);
        }

        List<Account> applicants = jobApplication.getApplicants();
        if (applicants == null) {
            applicants = new ArrayList<>(List.of(account));
        } else {
            applicants.add(account);
        }

        jobApplication.setApplicants(applicants);
        account.setJobApplications(accountApplications);

        accountService.save(account);
        repository.save(jobApplication);
    }

    public List<JobApplication> search(@NotNull String query,
                                       DatePosted datePosted, List<EmploymentType> employmentTypes,
                                       List<ExperienceLevel> experienceLevels, List<Category> categories) {
        String searchUrl = getUrl(query, datePosted, employmentTypes, experienceLevels, categories);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(searchUrl))
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", API_HOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ignored) {
        }
        return gson.fromJson(response.body(), JobApplicationData.class).getJobApplicationJSONs()
                .stream()
                .map(ja ->
                        new JobApplication(ja, locationService.existsByCity(ja.getJobCity()) ?
                                locationService.getByCity(ja.getJobCity()) :
                                locationService.save(
                                        new Location(ja.getJobCity(), ja.getJobLatitude(),
                                                ja.getJobLongitude(), ja.getJobCountry())))
                )
                .toList();
    }

    public List<JobApplication> search(@NotNull String query) {
        String searchUrl = getUrl(query);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(searchUrl))
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", API_HOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ignored) {
        }
        System.out.println(response.body());
        return gson.fromJson(response.body(), JobApplicationData.class).getJobApplicationJSONs()
                .stream()
                .map(ja ->
                        new JobApplication(ja, locationService.existsByCity(ja.getJobCity()) ?
                                locationService.getByCity(ja.getJobCity()) :
                                locationService.save(
                                        new Location(ja.getJobCity(), ja.getJobLatitude(),
                                                ja.getJobLongitude(), ja.getJobCountry())))
                )
                .toList();
    }

    private String getUrl(@NotNull String query,
                          DatePosted datePosted, List<EmploymentType> employmentTypes,
                          List<ExperienceLevel> experienceLevels, List<Category> categories) {
        String validQuery = query.replace(" ", "%20").replace(",", "%2C");
        String datePostedValue = datePosted.getValue();
        String empTypesValues = String.join("%2C", employmentTypes.stream().map(EmploymentType::getValue).toList());
        String expLevelsValues = String.join("%2C", experienceLevels.stream().map(ExperienceLevel::getValue).toList());
        String categoriesValues = String.join("%2C", categories.stream().map(Category::getValue).toList());

        return "https://jsearch.p.rapidapi.com/search?query=%s&page=1&num_pages=1&date_posted=%s&remote_jobs_only=false&employment_types=%s&job_requirements=%s&company_types=%s&actively_hiring=true"
                .formatted(validQuery, datePostedValue, empTypesValues, expLevelsValues, categoriesValues);
    }

    private String getUrl(@NotNull String query) {
        String validQuery = query.replace(" ", "%20").replace(",", "%2C");
        String datePostedAllValue = DatePosted.ALL.getValue();
        String empTypesAllValues = String.join("%2C", Arrays.stream(EmploymentType.values()).map(EmploymentType::getValue).toList());
        String expLevelsAllValues = String.join("%2C", Arrays.stream(ExperienceLevel.values()).map(ExperienceLevel::getValue).toList());
        String categoriesAllValues = String.join("%2C", Arrays.stream(Category.values()).map(Category::getValue).toList());

        return "https://jsearch.p.rapidapi.com/search?query=%s&page=1&num_pages=1&date_posted=%s&remote_jobs_only=false&employment_types=%s&job_requirements=%s&company_types=%s&actively_hiring=true"
                .formatted(validQuery, datePostedAllValue, empTypesAllValues, expLevelsAllValues, categoriesAllValues);

    }
}
