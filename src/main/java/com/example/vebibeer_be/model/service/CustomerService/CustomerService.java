package com.example.vebibeer_be.model.service.CustomerService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.Config.JwtTokenUtil;
import com.example.vebibeer_be.dto.UserRegistrationDto;
import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.entities.Customer.TypeCustomer;
import com.example.vebibeer_be.model.repo.CustomerRepo.CustomerRepo;
import com.example.vebibeer_be.model.repo.CustomerRepo.TypeCustomerRepo;
import com.example.vebibeer_be.model.service.UserDetailsServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    TypeCustomerRepo typeCustomerRepo;

    public List<Customer> getAllCustomer() {
        return customerRepo.findAll();
    }

    public Customer getCustomerById(int customer_id) {
        return customerRepo.getReferenceById(customer_id);
    }

    public void saveCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    public void deleteCustomer(int customer_id) {
        customerRepo.deleteById(customer_id);
    }

    public List<TypeCustomer> getAllTypeCustomer() {
        return typeCustomerRepo.findAll();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public void register(UserRegistrationDto registrationDto) {
        Customer user = new Customer();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setCustomer_status("not_confirmed");
        customerRepo.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(registrationDto.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        try {
            sendVerificationEmail(user, token);
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }
        

    }

    public Optional<Customer> findByUsername(String username) {
        return customerRepo.findByUsername(username);
    }

    @Autowired
    JavaMailSender mailSender;

    private void sendVerificationEmail(Customer user, String token)
            throws MessagingException, UnsupportedEncodingException {
                
        String toAddress = user.getUsername();
        String fromAddress = "chumlu2102@gmail.com";
        String senderName = "Vebibeer";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = "http://localhost:8080/api/register/verify?token=" + token + "&username="
                + user.getUsername();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        mailSender.send(message);

    }

}
