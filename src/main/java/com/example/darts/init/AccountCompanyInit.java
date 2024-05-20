package com.example.darts.init;

import com.example.darts.model.entity.Account;
import com.example.darts.model.entity.Company;
import com.example.darts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountCompanyInit implements CommandLineRunner {
    private final AccountRepository repository;


    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            return;
        }

        for (Account account : repository.findAll()) {
            if(repository.existsByCompanies_email(account.getEmail())){
                continue;
            }
            account.getCompanies().add(new Company(account));
        }
    }
}

