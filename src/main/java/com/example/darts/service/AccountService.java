package com.example.darts.service;

import com.example.darts.model.binding.AccountEditBindingModel;
import com.example.darts.model.binding.AccountRegisterBindingModel;
import com.example.darts.model.entity.Account;
import com.example.darts.model.entity.Company;
import com.example.darts.model.entity.Location;
import com.example.darts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final PasswordEncoder encoder;
    private final CloudinaryService cloudinaryService;
    private final CompanyService companyService;

    public void register(AccountRegisterBindingModel accountRegisterBindingModel) {
        // Create Account entity from the binding model and encode the password
        Account account = new Account(accountRegisterBindingModel);
        account.setPassword(encoder.encode(account.getPassword()));

        // Save the Account entity first
        repository.save(account);

        // Create and save the Company entity with a reference to the saved Account
        Company company = new Company(account);
        account.setCompanies(new ArrayList<>(List.of(company)));

        // Save the Company entity
        companyService.save(company);

        // Update the Account entity with the reference to the saved Company and save it again if needed
        repository.save(account);
    }

    public Account getByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public void edit(Account account, AccountEditBindingModel bindingModel) {
        account.setFirstName(bindingModel.getFirstName());
        account.setLastName(bindingModel.getLastName());
        account.setEmail(bindingModel.getEmail());
        account.setLocation(bindingModel.getLocation());
        account.setPhone(bindingModel.getPhone());
        account.setAbout(bindingModel.getAbout());
        account.setSkills(bindingModel.getSkills());

        if(bindingModel.getPhoto() != null) {
            String photoUrl = cloudinaryService.uploadImage(bindingModel.getPhoto());
            account.setPhoto(photoUrl);
        }
        if (bindingModel.getCv() != null) {
            String cvUrl = cloudinaryService.uploadImage(bindingModel.getCv());
            account.setCv(cvUrl);
        }

        repository.save(account);
    }

    public void save(Account account) {
        repository.save(account);
    }
}
