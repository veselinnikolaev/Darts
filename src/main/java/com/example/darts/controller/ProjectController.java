package com.example.darts.controller;

import com.example.darts.model.binding.ProjectCreateBindingModel;
import com.example.darts.model.entity.Account;
import com.example.darts.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/projects")
public class ProjectController extends BaseController {
    public ProjectController(AccountService accountService, LocationService locationService, CompanyService companyService, SkillService skillService, JobApplicationService jobApplicationService, ProjectService projectService) {
        super(accountService, locationService, companyService, skillService, jobApplicationService, projectService);
    }

    @GetMapping("/post")
    public ModelAndView create(@ModelAttribute(name = "project") ProjectCreateBindingModel bindingModel) {
        return new ModelAndView("/project/post")
                .addObject("allSkills", skillService.getAll());
    }

    @PostMapping("/post")
    public ModelAndView createConfirm(@ModelAttribute(name = "project") @Valid ProjectCreateBindingModel bindingModel,
                                      BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/project/post")
                    .addObject("allSkills", skillService.getAll());
        }
        Account account = accountService.getByEmail(principal.getName());
        projectService.save(bindingModel, account);

        return new ModelAndView("redirect:/account/profile");
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable Long id) {
        return new ModelAndView("/project/details")
                .addObject("project", projectService.getById(id));
    }
}
