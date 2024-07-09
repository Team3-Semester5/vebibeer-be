package com.example.vebibeer_be.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

import com.example.vebibeer_be.dto.PasswordChangeDTO;
import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.service.BusCompanyService.BusCompanyService;
import com.example.vebibeer_be.model.service.CustomerService.CustomerService;
import com.example.vebibeer_be.payload.AuthBusResponse;
import com.example.vebibeer_be.payload.AuthResponse;
import com.example.vebibeer_be.payload.LoginRequest;
import com.example.vebibeer_be.payload.SignUpRequest;
import com.example.vebibeer_be.security.TokenProvider;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api")
public class JwtAuthenticationController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    BusCompanyService busCompanyService = new BusCompanyService();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Customer customer = customerService.findByUsername(loginRequest.getUsername()).orElse(new Customer());
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token, customer));
    }

    @PostMapping("/bus/authenticate")
    public ResponseEntity<?> authenticateBus(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        BusCompany busCompany = busCompanyService.findByUsername(loginRequest.getUsername());
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthBusResponse(token, busCompany));
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
    public ResponseEntity<?> forgetPassword(@RequestParam("username") String username) {
        Optional<Customer> customer = customerService.findByUsername(username);

        for (Customer oldCustomer : customerService.getAllCustomer()) {
            if (username.equals(oldCustomer.getUsername())) {
                oldCustomer.setPassword(passwordEncoder.encode("hello1234"));
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                oldCustomer.getUsername(),
                                "hello1234"));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = tokenProvider.createToken(authentication);
                try {
                    customerService.sendChangePasswordEmail(oldCustomer, token,
                            "http://localhost:3000/changePassword");
                } catch (UnsupportedEncodingException | MessagingException e) {
                    e.printStackTrace();
                }
                return ResponseEntity.ok(new AuthResponse(token, oldCustomer));
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam("token") String token,
            @RequestParam("username") String username, @RequestBody PasswordChangeDTO passwordChangeDTO) {
        if (tokenProvider.validateToken(token)) {
            if (passwordChangeDTO.getOldPassword() == null ||
                    passwordChangeDTO.getNewPassword() == null ||
                    passwordChangeDTO.getConfirmPassword() == null) {
                return new ResponseEntity<>("Missing required fields", HttpStatus.BAD_REQUEST);
            }

            Customer existingCustomer = customerService.findByUsername(username).orElse(null);
            if (existingCustomer == null) {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }

            if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getConfirmPassword())) {
                return new ResponseEntity<>("New password and confirm password do not match",
                        HttpStatus.BAD_REQUEST);
            }

            existingCustomer.setPassword(passwordChangeDTO.getNewPassword());
            customerService.saveCustomer(existingCustomer);

            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);

    }

    @PostMapping("/auth/google")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> tokenMap) {
        String token = tokenMap.get("token");
        try {
            // Verify the token
            Jwt jwt = JwtDecoders.fromOidcIssuerLocation("https://accounts.google.com").decode(token);
            // Implement your own logic to create/find user in your database
            String email = jwt.getClaimAsString("email");
            Customer customer = customerService.findByUsername(email).orElse(null);
            if (customer == null) {
                SignUpRequest signUpRequest = new SignUpRequest();
                signUpRequest.setUsername(email);
                signUpRequest.setFullname(jwt.getClaimAsString("name"));
                signUpRequest.setPassword("123456789");
                customerService.register(signUpRequest);
            }
            return ResponseEntity.ok(new AuthResponse(token, customer));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

}
