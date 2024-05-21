package com.example.darts.service;

import com.example.darts.model.binding.AccountRegisterBindingModel;
import com.example.darts.model.entity.Account;
import com.example.darts.model.entity.Location;
import com.example.darts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final PasswordEncoder encoder;
    public void register(AccountRegisterBindingModel accountRegisterBindingModel, Location location) {
        Account account = new Account(accountRegisterBindingModel, location);
        account.setPassword(encoder.encode(account.getPassword()));

        repository.save(account);
    }

    public Account getByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
