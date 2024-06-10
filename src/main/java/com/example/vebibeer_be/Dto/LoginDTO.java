package com.example.vebibeer_be.Dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginDTO {
    @NotBlank(message = "Username is mandatory")
    @Email
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;
}