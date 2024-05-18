package com.example.darts.service;

import com.example.darts.model.binding.AccountRegisterBindingModel;
import com.example.darts.model.entity.Account;
import com.example.darts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final PasswordEncoder encoder;
    public void register(AccountRegisterBindingModel accountRegisterBindingModel) {
        Account account = new Account(accountRegisterBindingModel);
        account.setPassword(encoder.encode(account.getPassword()));

        repository.save(account);
    }

    public Account findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
