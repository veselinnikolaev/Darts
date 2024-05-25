package com.example.darts.service;

import com.example.darts.model.binding.CompanyBindingModel;
import com.example.darts.model.entity.Company;
import com.example.darts.model.entity.Location;
import com.example.darts.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository repository;
    private final CloudinaryService cloudinaryService;

    public Company getById(Long company) {
        return repository.findById(company).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public void save(CompanyBindingModel bindingModel) {
        String logoUrl = null;
        if (bindingModel.getLogo() != null) {
            logoUrl = cloudinaryService.uploadImage(bindingModel.getLogo());
        }

        repository.save(new Company(bindingModel, logoUrl));
    }

    public void save(Company company) {
        repository.save(company);
    }
}
