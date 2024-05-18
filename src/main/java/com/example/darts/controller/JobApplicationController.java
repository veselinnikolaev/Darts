package com.example.darts.controller;

import com.example.darts.model.entity.JobApplication;
import com.example.darts.model.enumeration.Category;
import com.example.darts.repository.LocationRepository;
import com.example.darts.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobApplicationController {
    private final JobApplicationService service;
    private final LocationRepository locationRepository;
    @GetMapping("/all")
    public ModelAndView jobs() {
        return new ModelAndView("job_listing")
                .addObject("jobs", service.getAllJobApplications())
                .addObject("service", service)
                .addObject("locations", locationRepository.findAll());
    }

    @GetMapping("/{id}")
    public ModelAndView jobDetails(@PathVariable Long id) {
        JobApplication job = service.getJobApplicationById(id);

        return new ModelAndView("job_details").addObject("job", job);
    }

    @GetMapping("/{category}")
    public ModelAndView jobDetails(@PathVariable String category){
        return new ModelAndView("job_listing")
                .addObject("jobs", service.getJobApplicationsByCategory(Category.valueOf(category.toUpperCase())))
                .addObject("service", service)
                .addObject("locations", locationRepository.findAll());
    }

    @PostMapping("/search")
    public ModelAndView search(@RequestParam(name="keyword", required = false, defaultValue = "all") String keyword,
                               @RequestParam(name="location", required = false, defaultValue = "all") String location) {
        return new ModelAndView("job_listing")
                .addObject("jobs", service.getAllJobApplications(keyword, location))
                .addObject("service", service)
                .addObject("locations", locationRepository.findAll());
    }
}
