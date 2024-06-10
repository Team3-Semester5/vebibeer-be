package com.example.vebibeer_be.controller;

import com.example.vebibeer_be.Dto.UserRegistrationDTO;
import com.example.vebibeer_be.Dto.VerificationDTO;
import com.example.vebibeer_be.model.service.UserService;
import com.example.vebibeer_be.Dto.LoginDTO;
import com.example.vebibeer_be.payload.response.LoginResponse;
import com.example.vebibeer_be.payload.response.RegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        logger.info("Registering user: {}", userRegistrationDTO.getUsername());
        RegistrationResponse response = userService.registerUser(userRegistrationDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<LoginResponse> verifyUser(@Valid @RequestBody VerificationDTO verificationDTO) {
        logger.info("Verifying user: {}", verificationDTO.getUsername());
        LoginResponse response = userService.verifyUser(verificationDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDTO loginDTO) {
        logger.info("Logging in user: {}", loginDTO.getUsername());
        LoginResponse response = userService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
}