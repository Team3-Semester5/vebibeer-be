package com.example.vebibeer_be.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.service.CustomerService.CustomerService;
import com.example.vebibeer_be.payload.AuthResponse;
import com.example.vebibeer_be.payload.LoginRequest;
import com.example.vebibeer_be.payload.SignUpRequest;
import com.example.vebibeer_be.security.TokenProvider;

@RestController
@RequestMapping("/api")
public class JwtAuthenticationController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Customer customer = customerService.findByUsername(loginRequest.getUsername()).orElse(new Customer());
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token, customer));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        customerService.register(signUpRequest);
        return ResponseEntity.ok(signUpRequest);
    }

    @GetMapping("/register/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token,
            @RequestParam("username") String username) {
        if (tokenProvider.validateToken(token)) {
            Optional<Customer> customer = customerService.findByUsername(username);
            for (Customer oldCustomer : customerService.getAllCustomer()) {
                if (username.equals(oldCustomer.getUsername())) {
                    oldCustomer.setCustomer_status("confirmed");
                    System.out.println(oldCustomer.toString());
                    customerService.saveCustomer(oldCustomer);
                }
            }
            return ResponseEntity.ok(customer);
        }
        return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
    }

    @GetMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestParam("token") String token,
            @RequestParam("username") String username) {
        if (tokenProvider.validateToken(token)) {
            Optional<Customer> customer = customerService.findByUsername(username);
            for (Customer oldCustomer : customerService.getAllCustomer()) {
                if (username.equals(oldCustomer.getUsername())) {
                    return ResponseEntity.ok(customer);
                }
            }
            
        }
        return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
    }

    

}
