package com.example.vebibeer_be.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import com.example.vebibeer_be.Config.JwtTokenUtil;
import com.example.vebibeer_be.dto.UserRegistrationDto;
import com.example.vebibeer_be.model.entities.User;
import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.service.UserDetailsServiceImpl;
import com.example.vebibeer_be.model.service.CustomerService.CustomOAuth2UserService;
import com.example.vebibeer_be.model.service.CustomerService.CustomerService;

@RestController
@RequestMapping("/api")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CustomerService userService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
        Customer loginCustomer = new Customer();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            for (Customer customer : userService.getAllCustomer()) {
                if (customer.getUsername().equals(user.getUsername())) {
                    if (!customer.getCustomer_status().equalsIgnoreCase("confirmed")) {
                        return new ResponseEntity<>(HttpStatus.LOCKED);
                    }
                    loginCustomer = new Customer(customer.getCustomer_id(), customer.getCustomer_status(),
                            customer.getCustomer_fullname(), customer.getCustomer_dob(), customer.getCustomer_img_ava(),
                            customer.getCustomer_nationality(), customer.getCustomer_gender(),
                            customer.getCustomer_description(), customer.isVerify_purchased(), customer.getPoint(),
                            customer.getTypeCustomer(), customer.getTransactions());
                }
            }
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        System.out.println("hello");
        return ResponseEntity.ok(new AuthResponse(new JwtResponse(jwt), loginCustomer));
    }

    public class AuthResponse {
        private JwtResponse jwtResponse;
        private Customer customer;
    
        public AuthResponse(JwtResponse jwtResponse, Customer customer) {
            this.jwtResponse = jwtResponse;
            this.customer = customer;
        }
    
        // Getters and setters
        public JwtResponse getJwtResponse() {
            return jwtResponse;
        }
    
        public void setJwtResponse(JwtResponse jwtResponse) {
            this.jwtResponse = jwtResponse;
        }
    
        public Customer getCustomer() {
            return customer;
        }
    
        public void setCustomer(Customer customer) {
            this.customer = customer;
        }
    }

    // JWT response class
    private static class JwtResponse {
        private final String jwt;

        public JwtResponse(String jwt) {
            this.jwt = jwt;
        }

        public String getJwt() {
            return jwt;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        userService.register(registrationDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/register/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token,
            @RequestParam("username") String username) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.validateToken(token, userDetails)) {
            Optional<Customer> customer = userService.findByUsername(username);
            for (Customer oldCustomer : userService.getAllCustomer()) {
                if (username.equals(oldCustomer.getUsername())) {
                    oldCustomer.setCustomer_status("confirmed");
                    System.out.println(oldCustomer.toString());
                    userService.saveCustomer(oldCustomer);
                }
            }
            return ResponseEntity.ok(customer);
        }
        return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
    }

    @GetMapping("/loginGoogle")
    public ResponseEntity<?> loginGoogle(@AuthenticationPrincipal OAuth2User user) throws Exception {
        String email = user.getAttribute("email");

        // Directly fetch customer by username (email in this case)
        Optional<Customer> customer = userService.findByUsername(email);
        if (customer == null) {
            // Handle case where customer is not found, possibly register a new customer
            UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
            userRegistrationDto.setUsername(email);
            userRegistrationDto.setPassword("vebibeer123");
            userService.register(userRegistrationDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        for (Customer googlCustomer : userService.getAllCustomer()) {
            if (googlCustomer.getUsername().equals(email)) {
                if (!googlCustomer.getCustomer_status().equalsIgnoreCase("confirmed")) {
                    return new ResponseEntity<>(HttpStatus.LOCKED);
                }
            }
        }

        // Generate JWT using details from the authenticated OAuth2User
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok().header("Cross-Origin-Opener-Policy", "same-origin").body("Sensitive data");
    }

}
