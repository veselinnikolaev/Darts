package com.example.darts.model.binding;

import com.example.darts.validation.annotation.ConfirmPassword;
import com.example.darts.validation.annotation.Phone;
import com.example.darts.validation.annotation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountRegisterBindingModel {
    @NotBlank
    @Size(min = 3, max = 20, message = "First name length must be between 3 and 20 characters!")
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 20, message = "Last name length must be between 3 and 20 characters!")
    private String lastName;
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    @NotBlank
    private String password;
    @ConfirmPassword
    @NotBlank
    private String confirmPassword;
    @Email(message = "Invalid email!")
    @UniqueEmail
    @NotBlank(message = "Email cannot be empty!")
    private String email;
    //@Phone
    @NotBlank
    private String phone;
    private String about;
}
