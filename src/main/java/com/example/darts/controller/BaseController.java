package com.example.darts.controller;

import com.example.darts.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
public abstract class BaseController {
    protected final AccountService accountService;
    protected final LocationService locationService;
    protected final CompanyService companyService;
    protected final ExperienceService experienceService;
    protected final SkillService skillService;
    protected final JobApplicationService jobApplicationService;
    public ModelAndView getWithLocations(String name){
        return new ModelAndView(name)
                .addObject("locations", locationService.getAll());
    }

    public ModelAndView getWithLocationsExperiencesAndSkills(String name){
        return getWithLocations(name)
                .addObject("experiences", experienceService.getAll())
                .addObject("skills",skillService.getAll());
    }
}
