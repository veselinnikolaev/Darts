package com.example.darts.validation.validator;

import com.example.darts.repository.AccountRepository;
import com.example.darts.validation.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final AccountRepository repository;

    public UniqueEmailValidator(AccountRepository repository) {
        this.repository = repository;
    }
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return this.repository.findByEmail(email).isEmpty();
    }
}