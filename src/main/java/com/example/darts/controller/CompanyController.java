package com.example.darts.controller;

import com.example.darts.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService service;
}
