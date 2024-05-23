package com.example.darts.controller;

import com.example.darts.model.entity.JobApplication;
import com.example.darts.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController{
    public HomeController(AccountService accountService, LocationService locationService, CompanyService companyService, SkillService skillService, JobApplicationService jobApplicationService, ProjectService projectService) {
        super(accountService, locationService, companyService, skillService, jobApplicationService, projectService);
    }

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mv = getWithLocations("/common/index");
        return mv.addObject("jobApplicationService", jobApplicationService)
                .addObject("newestJobs", jobApplicationService.getTop5JobApplicationsByDatePosted());
    }

    @PostMapping("/")
    public ModelAndView search(String keyword,
                               String location,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobApplication> jobsPage = jobApplicationService.getAll(keyword, location, pageable);
        return getWithLocations("/job/listing")
                .addObject("jobsPage", jobsPage)
                .addObject("jobApplicationService", jobApplicationService);
    }

    @GetMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("/common/about");
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("/common/contact");
    }
}
