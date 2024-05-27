package com.example.darts.controller;

import com.example.darts.model.binding.JobApplicationBindingModel;
import com.example.darts.model.entity.Account;
import com.example.darts.model.entity.JobApplication;
import com.example.darts.model.entity.Location;
import com.example.darts.model.enumeration.Category;
import com.example.darts.model.enumeration.DatePosted;
import com.example.darts.model.enumeration.EmploymentType;
import com.example.darts.model.enumeration.ExperienceLevel;
import com.example.darts.model.json.JobApplicationJSON;
import com.example.darts.service.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobApplicationController extends BaseController {
    public JobApplicationController(AccountService accountService, LocationService locationService, CompanyService companyService, SkillService skillService, JobApplicationService jobApplicationService, ProjectService projectService) {
        super(accountService, locationService, companyService, skillService, jobApplicationService, projectService);
    }

    @GetMapping("/all")
    public ModelAndView jobs(@RequestParam(required = false) String keyword, @RequestParam(required = false) String location,
                             @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
                             @RequestParam(value = "category", required = false) Category category,
                             @RequestParam(value = "employmentTypes", required = false) List<EmploymentType> employmentTypes,
                             @RequestParam(value = "experienceLevels", required = false) List<ExperienceLevel> experienceLevels,
                             @RequestParam(value = "postedWithin", required = false) DatePosted postedWithin) {
        List<JobApplication> jobApplications;
        if (category == null || employmentTypes == null || experienceLevels == null || postedWithin == null) {
            jobApplications = new ArrayList<>(jobApplicationService.search(keyword));
        } else {
            jobApplications = new ArrayList<>(jobApplicationService.search(
                    keyword, postedWithin, employmentTypes, experienceLevels, List.of(category)
            ));
        }

        if (!location.equals("all")) {
            Location locationEntity = locationService.getById(Long.parseLong(location));
            jobApplications = jobApplications.stream()
                    .filter(ja -> ja.getLocation().getCity().equals(locationEntity.getCity()))
                    .toList();
        }
        if(jobApplicationService.getAll(keyword, location) != null && !jobApplications.isEmpty()) {
            jobApplications.addAll(jobApplicationService.getAll(keyword, location));
        }
        Page<JobApplication> jobsPage =
                new PageImpl<>(jobApplications, PageRequest.of(page, size), jobApplications.size());
        return getWithLocations("/job/listing")
                .addObject("jobsPage", jobsPage)
                .addObject("jobApplicationService", jobApplicationService)
                .addObject("keyword", keyword)
                .addObject("locationId", location);
    }

    @GetMapping("/details/{id}")
    public ModelAndView jobDetails(@PathVariable Long id) {
        JobApplication job = jobApplicationService.getById(id);
        return new ModelAndView("job/details")
                .addObject("job", job);
    }

    @GetMapping("/post")
    public ModelAndView postJob(@ModelAttribute(name = "jobApplication") JobApplicationBindingModel bindingModel, Principal principal) {
        Account account = accountService.getByEmail(principal.getName());

        return getWithLocationsExperienceLevelsAndSkills("/job/post")
                .addObject("companies", account.getCompanies());
    }

    @PostMapping("/post")
    public ModelAndView postJobConfirm(@ModelAttribute(name = "jobApplication") @Valid JobApplicationBindingModel bindingModel, BindingResult bindingResult, Principal principal) {
        Account account = accountService.getByEmail(principal.getName());
        if (bindingResult.hasErrors()) {
            return getWithLocationsExperienceLevelsAndSkills("/job/post")
                    .addObject("companies", account.getCompanies());
        }
        jobApplicationService.save(bindingModel);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/apply/{id}")
    public ModelAndView applyForJob(@PathVariable Long id, Principal principal) {
        Account account = accountService.getByEmail(principal.getName());
        JobApplication jobApplication = jobApplicationService.getById(id);

        jobApplicationService.apply(jobApplication, account);

        return new ModelAndView("redirect:/");
    }
}
