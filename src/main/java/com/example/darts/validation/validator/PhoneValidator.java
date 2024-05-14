package com.example.darts.validation.validator;

import com.example.darts.validation.annotation.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return phone.matches("^\\+\\d{1,3}\\s?\\(?\\d{1,3}?\\)?\\s?\\d{1,5}\\s?\\d{1,4}\\s?\\d{1,9}$");
    }
}
