package com.example.darts.validation.validator;

import com.example.darts.model.binding.AccountBindingModel;
import com.example.darts.validation.annotation.ConfirmPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, String> {
    private static String passwordToConfirm;
    private static String confirmPassword;
    public static void getObject(AccountBindingModel userRegisterBindingModel){
        passwordToConfirm = userRegisterBindingModel.getPassword();
        confirmPassword = userRegisterBindingModel.getConfirmPassword();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        confirmPassword = confirmPassword != null ? confirmPassword : "";
        return confirmPassword.equals(passwordToConfirm != null ? passwordToConfirm : "");
    }
}
