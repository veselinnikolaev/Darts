package com.example.darts.controller;

import com.example.darts.model.binding.CompanyBindingModel;
import com.example.darts.model.entity.Location;
import com.example.darts.service.CompanyService;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService service;
    private final LocationService locationService;

    @GetMapping("/post")
    public ModelAndView postCompany(@ModelAttribute(name = "company") CompanyBindingModel bindingModel){
        return new ModelAndView("/company/company_post").addObject("locations", locationService.getAll());
    }

    @PostMapping("/post")
    public ModelAndView postCompanyConfirm(@ModelAttribute(name = "company") @Valid CompanyBindingModel bindingModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("/company/company_post").addObject("locations", locationService.getAll());
        }
        List<Location> locations = bindingModel.getLocations().stream().map(locationService::getById).toList();

        service.postCompany(bindingModel, locations);
        return new ModelAndView("redirect:/");
    }
}
