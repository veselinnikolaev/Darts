package com.example.darts.controller;

import com.example.darts.model.binding.JobApplicationBindingModel;
import com.example.darts.model.entity.Account;
import com.example.darts.model.entity.JobApplication;
import com.example.darts.model.enumeration.Category;
import com.example.darts.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobApplicationController {
    private final JobApplicationService service;
    private final LocationService locationService;
    private final AccountService accountService;
    private final ExperienceService experienceService;
    private final SkillService skillService;

    @GetMapping("/all")
    public ModelAndView jobs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobApplication> jobsPage = service.getAllJobApplications(pageable);
        return new ModelAndView("/job/job_listing")
                .addObject("jobsPage", jobsPage)
                .addObject("service", service)
                .addObject("locations", locationService.getAll());
    }

    @GetMapping("/details/{id}")
    public ModelAndView jobDetails(@PathVariable Long id) {
        JobApplication job = service.getJobApplicationById(id);
        return new ModelAndView("/job/job_details").addObject("job", job);
    }

    @GetMapping("/all/{category}")
    public ModelAndView jobDetails(@PathVariable String category, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobApplication> jobsPage = service.getJobApplicationsByCategory(Category.valueOf(category.toUpperCase()), pageable);
        return new ModelAndView("/job/job_listing")
                .addObject("jobsPage", jobsPage)
                .addObject("service", service)
                .addObject("locations", locationService.getAll());
    }
    @GetMapping("/post")
    public ModelAndView postJob(@ModelAttribute(name = "jobApplication") JobApplicationBindingModel bindingModel, Principal principal) {
        Account account = accountService.findByEmail(principal.getName());

        return new ModelAndView("/job/job_post")
                .addObject("locations", locationService.getAll())
                .addObject("companies", account.getCompanies())
                .addObject("experiences", experienceService.getAll())
                .addObject("skills", skillService.getAll());
    }

    @PostMapping("/post")
    public ModelAndView postJobConfirm(@ModelAttribute(name = "jobApplication") @Valid JobApplicationBindingModel bindingModel, BindingResult bindingResult, Principal principal) {
        Account account = accountService.findByEmail(principal.getName());
        if(bindingResult.hasErrors()) {
            return new ModelAndView("/job/job_post")
                    .addObject("locations", locationService.getAll())
                    .addObject("companies", account.getCompanies())
                    .addObject("experiences", experienceService.getAll())
                    .addObject("skills", skillService.getAll());
        }
        service.saveJobApplication(bindingModel, account);
        return new ModelAndView("redirect:/jobs/all");
    }
}
