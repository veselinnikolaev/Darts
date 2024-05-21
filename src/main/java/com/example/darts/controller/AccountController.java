package com.example.darts.controller;

import com.example.darts.model.binding.AccountRegisterBindingModel;
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

import java.security.Principal;

@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {
    public AccountController(AccountService accountService, LocationService locationService, CompanyService companyService, ExperienceService experienceService, SkillService skillService, JobApplicationService jobApplicationService) {
        super(accountService, locationService, companyService, experienceService, skillService, jobApplicationService);
    }

    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute(name = "account") AccountRegisterBindingModel account) {
        return getWithLocations("/account/register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute(name = "account") @Valid AccountRegisterBindingModel accountRegisterBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getWithLocations("/account/register");
        }
        Location location = locationService.getById(accountRegisterBindingModel.getLocation());
        accountService.register(accountRegisterBindingModel, location);
        return new ModelAndView("redirect:/account/login");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/account/login");
    }

    @GetMapping("/login/error")
    public ModelAndView loginError(@ModelAttribute(name = "email") String email) {
        return new ModelAndView("/account/login")
                .addObject("bad_credentials", true);
    }

    @GetMapping("/profile")
    public ModelAndView profile() {
        return new ModelAndView("/account/profile");
    }
}
