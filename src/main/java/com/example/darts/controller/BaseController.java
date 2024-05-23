package com.example.darts.controller;

import com.example.darts.model.enumeration.ExperienceLevel;
import com.example.darts.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
public abstract class BaseController {
    protected final AccountService accountService;
    protected final LocationService locationService;
    protected final CompanyService companyService;
    protected final SkillService skillService;
    protected final JobApplicationService jobApplicationService;
    protected final ProjectService projectService;
    public ModelAndView getWithLocations(String name){
        return new ModelAndView(name)
                .addObject("allLocations", locationService.getAll());
    }

    public ModelAndView getWithLocationsExperienceLevelsAndSkills(String name){
        return getWithLocations(name)
                .addObject("allExperienceLevels", ExperienceLevel.values())
                .addObject("allSkills",skillService.getAll());
    }
}
