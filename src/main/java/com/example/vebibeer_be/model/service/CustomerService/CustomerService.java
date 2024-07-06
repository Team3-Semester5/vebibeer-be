package com.example.vebibeer_be.model.service.CustomerService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.exception.BadRequestException;
import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.entities.Customer.TypeCustomer;
import com.example.vebibeer_be.model.repo.CustomerRepo.CustomerRepo;
import com.example.vebibeer_be.model.repo.CustomerRepo.TypeCustomerRepo;
import com.example.vebibeer_be.payload.SignUpRequest;
import com.example.vebibeer_be.security.TokenProvider;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private TypeCustomerRepo typeCustomerRepo;

    public List<Customer> getAllCustomer() {
        return customerRepo.findAll();
    }

    public Customer getCustomerById(int customer_id) {
        return customerRepo.findById(customer_id).get();
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
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void register(SignUpRequest customerSignup) {
        // if (customerRepo.findByUsername(customerSignup.getUsername()) != null) {
        // throw new BadRequestException("Email address already in use.");
        // }

        Customer user = new Customer();
        user.setCustomer_fullname(customerSignup.getFullname());
        user.setUsername(customerSignup.getUsername());
        user.setPassword(passwordEncoder.encode(customerSignup.getPassword()));
        user.setCustomer_status("not_confirmed");
        customerRepo.save(user);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customerSignup.getUsername(),
                        customerSignup.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        try {
            sendVerificationEmail(user, token, "http://localhost:8080/api/register/verify");
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }

    }

    public Optional<Customer> findByUsername(String username) {
        return customerRepo.findByUsername(username);
    }

    @Autowired
    JavaMailSender mailSender;

    public void sendVerificationEmail(Customer user, String token, String url)
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
        String verifyURL = url + "?token=" + token + "&username="
                + user.getUsername();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        mailSender.send(message);

    }

}
