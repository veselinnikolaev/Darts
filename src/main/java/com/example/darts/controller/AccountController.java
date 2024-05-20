package com.example.darts.controller;

import com.example.darts.model.binding.AccountRegisterBindingModel;
import com.example.darts.model.entity.Location;
import com.example.darts.service.AccountService;
import com.example.darts.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService service;
    private final LocationService locationService;
    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute(name = "account") AccountRegisterBindingModel account){
        return new ModelAndView("/account/register").addObject("locations", locationService.getAll());
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute(name = "account") @Valid AccountRegisterBindingModel accountRegisterBindingModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("/account/register").addObject("locations", locationService.getAll());
        }
        Location location = locationService.getById(accountRegisterBindingModel.getLocation());
        service.register(accountRegisterBindingModel, location);
        return new ModelAndView("redirect:/account/login");
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("/account/login");
    }

    @GetMapping("/login/error")
    public ModelAndView loginError(@ModelAttribute(name = "email") String email){
        return new ModelAndView("/account/login")
                .addObject("bad_credentials", true);
    }
}
