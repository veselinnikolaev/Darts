package com.example.darts.controller;

import com.example.darts.model.entity.Account;
import com.example.darts.model.entity.JobApplication;
import com.example.darts.model.enumeration.Category;
import com.example.darts.repository.LocationRepository;
import com.example.darts.service.AccountService;
import com.example.darts.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final JobApplicationService jobApplicationService;
    private final LocationRepository locationRepository;
    private final AccountService accountService;
    @GetMapping("/")
    public ModelAndView index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        ModelAndView mv = new ModelAndView("index");
        Pageable pageable = PageRequest.of(page, size);
        Map<Category, Long> categoryCounts = new HashMap<>();
        for (Category category : Category.values()) {
            List<JobApplication> jobApplications = jobApplicationService
                    .getJobApplicationsByCategory(category, pageable).getContent();
            long count = jobApplications.size();
            categoryCounts.put(category, count);
        }
        mv.addObject("categoryCounts", categoryCounts);
        mv.addObject("service", jobApplicationService);
        mv.addObject("locations", locationRepository.findAll());
        mv.addObject("newestJobs", jobApplicationService.getTop5JobApplicationsByDatePosted(pageable).getContent());
        return mv;
    }

    @GetMapping("/about")
    public ModelAndView about(Principal principal) {
        Account account = accountService.findByEmail(principal.getName());

        return new ModelAndView("about")
                .addObject("user", account);
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("contact");
    }
}
