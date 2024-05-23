package com.example.darts.controller;

import com.example.darts.model.binding.CompanyBindingModel;
import com.example.darts.model.entity.Location;
import com.example.darts.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController extends BaseController {
    public CompanyController(AccountService accountService, LocationService locationService, CompanyService companyService, SkillService skillService, JobApplicationService jobApplicationService, ProjectService projectService) {
        super(accountService, locationService, companyService, skillService, jobApplicationService, projectService);
    }

    @GetMapping("/post")
    public ModelAndView postCompany(@ModelAttribute(name = "company") CompanyBindingModel bindingModel) {
        return getWithLocations("/company/post");
    }

    @PostMapping("/post")
    public ModelAndView postCompanyConfirm(@ModelAttribute(name = "company") @Valid CompanyBindingModel bindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getWithLocations("/company/post");
        }
        companyService.save(bindingModel);
        return new ModelAndView("redirect:/");
    }
}
