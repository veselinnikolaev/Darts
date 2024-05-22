package com.example.darts.controller;

import com.example.darts.model.binding.JobApplicationBindingModel;
import com.example.darts.model.entity.Account;
import com.example.darts.model.entity.JobApplication;
import com.example.darts.model.enumeration.Category;
import com.example.darts.service.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/jobs")
public class JobApplicationController extends BaseController{
    public JobApplicationController(AccountService accountService, LocationService locationService, CompanyService companyService, ExperienceService experienceService, SkillService skillService, JobApplicationService jobApplicationService) {
        super(accountService, locationService, companyService, experienceService, skillService, jobApplicationService);
    }

    @GetMapping("/all")
    public ModelAndView jobs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobApplication> jobsPage = jobApplicationService.getAll(pageable);
        return getWithLocations("/job/listing")
                .addObject("jobsPage", jobsPage)
                .addObject("service", jobApplicationService);
    }

    @GetMapping("/details/{id}")
    public ModelAndView jobDetails(@PathVariable Long id) {
        JobApplication job = jobApplicationService.getById(id);
        return new ModelAndView("job/details")
                .addObject("job", job);
    }

    @GetMapping("/all/{category}")
    public ModelAndView jobDetails(@PathVariable String category, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobApplication> jobsPage = jobApplicationService.getAllByCategory(Category.valueOf(category.toUpperCase()), pageable);
        return getWithLocations("/job/listing")
                .addObject("jobsPage", jobsPage)
                .addObject("jobApplicationService", jobApplicationService);
    }
    @GetMapping("/post")
    public ModelAndView postJob(@ModelAttribute(name = "jobApplication") JobApplicationBindingModel bindingModel, Principal principal) {
        Account account = accountService.getByEmail(principal.getName());

        return getWithLocationsExperiencesAndSkills("/job/post")
                .addObject("companies", account.getCompanies());
    }

    @PostMapping("/post")
    public ModelAndView postJobConfirm(@ModelAttribute(name = "jobApplication") @Valid JobApplicationBindingModel bindingModel, BindingResult bindingResult, Principal principal) {
        Account account = accountService.getByEmail(principal.getName());
        if(bindingResult.hasErrors()) {
            return getWithLocationsExperiencesAndSkills("/job/post")
                    .addObject("companies", account.getCompanies());
        }
        jobApplicationService.save(bindingModel, account);
        return new ModelAndView("redirect:/jobs/all");
    }
}
