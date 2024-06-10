package com.example.vebibeer_be.model.service;

import com.example.vebibeer_be.Dto.UserRegistrationDTO;
import com.example.vebibeer_be.Dto.VerificationDTO;
import com.example.vebibeer_be.Dto.LoginDTO;
import com.example.vebibeer_be.payload.response.LoginResponse;
import com.example.vebibeer_be.payload.response.RegistrationResponse;

public interface UserService {
    RegistrationResponse registerUser(UserRegistrationDTO userRegistrationDTO);
    LoginResponse verifyUser(VerificationDTO verificationDTO);
    LoginResponse login(LoginDTO loginDTO);
}