package com.example.darts.controller;

import com.example.darts.model.binding.AccountBindingModel;
import com.example.darts.model.entity.Account;
import com.example.darts.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService service;
    @GetMapping("/register")
    public ModelAndView register(AccountBindingModel account){
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid AccountBindingModel accountBindingModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("register");
        }

        service.register(accountBindingModel);
        return new ModelAndView("redirect:login");
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @GetMapping("/login?error")
    public ModelAndView loginError(){
        return new ModelAndView("login").addObject("error", true);
    }
}
