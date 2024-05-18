package com.example.darts.controller;

import com.example.darts.model.entity.Account;
import com.example.darts.model.enumeration.Category;
import com.example.darts.repository.LocationRepository;
import com.example.darts.service.AccountService;
import com.example.darts.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final JobApplicationService jobApplicationService;
    private final LocationRepository locationRepository;
    private final AccountService accountService;
    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        for (Category category : Category.values()) {
            mv.addObject(category.toString().toLowerCase(), jobApplicationService.getJobApplicationsByCategory(category));
        }
        mv.addObject("service", jobApplicationService);
        mv.addObject("locations", locationRepository.findAll());
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
