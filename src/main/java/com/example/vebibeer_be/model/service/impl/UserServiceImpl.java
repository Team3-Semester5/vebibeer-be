package com.example.vebibeer_be.model.service.impl;

import com.example.vebibeer_be.Dto.UserRegistrationDTO;
import com.example.vebibeer_be.Dto.VerificationDTO;
import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.entities.Role;
import com.example.vebibeer_be.model.repo.RoleRepo;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.BusCompanyRepo;
import com.example.vebibeer_be.model.repo.CustomerRepo.CustomerRepo;
import com.example.vebibeer_be.model.service.UserService;
import com.example.vebibeer_be.Dto.LoginDTO;
import com.example.vebibeer_be.payload.response.LoginResponse;
import com.example.vebibeer_be.payload.response.RegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private BusCompanyRepo busCompanyRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Transactional
    public RegistrationResponse registerUser(UserRegistrationDTO userRegistrationDTO) {
        String role = userRegistrationDTO.getRole();
        Role roleEntity = roleRepo.findByRoleName(role);

        if (roleEntity == null || (!role.equalsIgnoreCase("BUS_COMPANY") && !role.equalsIgnoreCase("CUSTOMER"))) {
            return new RegistrationResponse("Invalid role specified", false);
        }

        if (role.equalsIgnoreCase("BUS_COMPANY")) {
            return registerBusCompany(userRegistrationDTO, roleEntity);
        } else if (role.equalsIgnoreCase("CUSTOMER")) {
            return registerCustomer(userRegistrationDTO, roleEntity);
        } else {
            return new RegistrationResponse("Invalid role specified", false);
        }
    }

    private RegistrationResponse registerBusCompany(UserRegistrationDTO userRegistrationDTO, Role role) {
        String verificationCode = generateVerificationCode();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(30);

        BusCompany busCompany = new BusCompany();
        busCompany.setUsername(userRegistrationDTO.getUsername());
        busCompany.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        busCompany.setBusCompany_status("Pending");
        busCompany.setBusCompany_fullname("");
        busCompany.setBusCompany_dob("");
        busCompany.setBusCompany_imgUrl("");
        busCompany.setBusCompany_description("");
        busCompany.setBusCompany_nationally("");
        busCompany.setBusCompany_name("");
        busCompany.setBusCompany_contract("");
        busCompany.setBusCompany_location("");
        busCompany.setVerificationCode(verificationCode);
        busCompany.setExpirationTime(expirationTime);
        busCompany.setEnabled(false);
        busCompany.setRole(role);

        busCompanyRepo.save(busCompany);
        sendVerificationEmail(busCompany.getUsername(), verificationCode);

        return new RegistrationResponse("Bus Company registered successfully", true);
    }

    private RegistrationResponse registerCustomer(UserRegistrationDTO userRegistrationDTO, Role role) {
        String verificationCode = generateVerificationCode();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(30);

        Customer customer = new Customer();
        customer.setUsername(userRegistrationDTO.getUsername());
        customer.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        customer.setCustomer_status("Pending");
        customer.setCustomer_fullname("");
        customer.setCustomer_dob(LocalDate.parse(""));
        customer.setCustomer_img_ava("");
        customer.setCustomer_description("");
        customer.setCustomer_nationality("");
        customer.setCustomer_gender("Not Specified");
        customer.setVerify_purchased(false);
        customer.setPoint(0);
        customer.setEnabled(false);
        customer.setRole(role);
        customer.setVerificationCode(verificationCode);
        customer.setExpirationTime(expirationTime);

        customerRepo.save(customer);
        sendVerificationEmail(customer.getUsername(), verificationCode);

        return new RegistrationResponse("Customer registered successfully", true);
    }

    @Override
    @Transactional
    public LoginResponse verifyUser(VerificationDTO verificationDTO) {
        Optional<BusCompany> busCompanyOptional = busCompanyRepo.findByUsernameAndVerificationCode(verificationDTO.getUsername(), verificationDTO.getVerificationCode());
        if (busCompanyOptional.isPresent()) {
            BusCompany busCompany = busCompanyOptional.get();
            if (busCompany.getExpirationTime().isAfter(LocalDateTime.now())) {
                busCompany.setEnabled(true);
                busCompanyRepo.save(busCompany);
                return new LoginResponse("Verification successful", true, "BUS_COMPANY");
            } else {
                return new LoginResponse("Verification code expired", false, null);
            }
        }

        Optional<Customer> customerOptional = customerRepo.findByUsernameAndVerificationCode(verificationDTO.getUsername(), verificationDTO.getVerificationCode());
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (customer.getExpirationTime().isAfter(LocalDateTime.now())) {
                customer.setEnabled(true);
                customerRepo.save(customer);
                return new LoginResponse("Verification successful", true, "CUSTOMER");
            } else {
                return new LoginResponse("Verification code expired", false, null);
            }
        }

        return new LoginResponse("Invalid verification code", false, null);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginDTO loginDTO) {
        Optional<BusCompany> busCompanyOptional = busCompanyRepo.findByUsername(loginDTO.getUsername());
        if (busCompanyOptional.isPresent()) {
            BusCompany busCompany = busCompanyOptional.get();
            if (passwordEncoder.matches(loginDTO.getPassword(), busCompany.getPassword())) {
                if (busCompany.isEnabled()) {
                    return new LoginResponse("Login successful", true, "BUS_COMPANY");
                } else {
                    return new LoginResponse("Account not verified", false, null);
                }
            } else {
                return new LoginResponse("Invalid credentials", false, null);
            }
        }

        Optional<Customer> customerOptional = customerRepo.findByUsername(loginDTO.getUsername());
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (passwordEncoder.matches(loginDTO.getPassword(), customer.getPassword())) {
                if (customer.isEnabled()) {
                    return new LoginResponse("Login successful", true, "CUSTOMER");
                } else {
                    return new LoginResponse("Account not verified", false, null);
                }
            } else {
                return new LoginResponse("Invalid credentials", false, null);
            }
        }

        return new LoginResponse("Invalid credentials", false, null);
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    private void sendVerificationEmail(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email Verification");
        message.setText("Your verification code is: " + verificationCode);
        mailSender.send(message);
    }
}
