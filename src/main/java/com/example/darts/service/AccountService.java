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

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final PasswordEncoder encoder;
    public void register(AccountRegisterBindingModel accountRegisterBindingModel) {
        Account account = new Account(accountRegisterBindingModel);
        account.setPassword(encoder.encode(account.getPassword()));
        account.setCompanies(List.of(new Company(account)));

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
        account.setPhoto(bindingModel.getPhoto().getOriginalFilename());
        account.setCV(bindingModel.getCv().getOriginalFilename());
        account.setSkills(bindingModel.getSkills());
        account.setExperiences(bindingModel.getExperiences());

        repository.save(account);
    }
}
