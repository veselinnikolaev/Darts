package com.example.darts.controller;

import com.example.darts.model.binding.AccountEditBindingModel;
import com.example.darts.model.binding.AccountRegisterBindingModel;
import com.example.darts.model.entity.Account;
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
    public AccountController(AccountService accountService, LocationService locationService, CompanyService companyService, SkillService skillService, JobApplicationService jobApplicationService, ProjectService projectService) {
        super(accountService, locationService, companyService, skillService, jobApplicationService, projectService);
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
        accountService.register(accountRegisterBindingModel);
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
    public ModelAndView profile(Principal principal) {
        return new ModelAndView("/account/profile")
                .addObject("account", accountService.getByEmail(principal.getName()));
    }

    @GetMapping("/edit")
    public ModelAndView edit(@ModelAttribute (name = "account") AccountEditBindingModel bindingModel, Principal principal) {
        return getWithLocationsExperienceLevelsAndSkills("/account/edit")
                .addObject("account", accountService.getByEmail(principal.getName()));
    }

    @PostMapping("/edit")
    public ModelAndView editConfirm(@ModelAttribute(name = "account") @Valid AccountEditBindingModel bindingModel, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()){
            return getWithLocationsExperienceLevelsAndSkills("/account/edit")
                    .addObject("account", accountService.getByEmail(principal.getName()));
        }
        Account account = accountService.getByEmail(principal.getName());
        accountService.edit(account, bindingModel);

        return new ModelAndView("redirect:/account/profile");
    }
}
