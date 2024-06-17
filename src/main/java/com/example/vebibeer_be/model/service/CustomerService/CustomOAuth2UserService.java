package com.example.vebibeer_be.model.service.CustomerService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.repo.CustomerRepo.CustomerRepo;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private CustomerRepo userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        return processUser(user);
    }

    private OAuth2User processUser(OAuth2User user) {
        Optional<Customer> existingUser = userRepository.findByUsername(user.getAttribute("email"));
        if (existingUser == null) {
            Customer newCustomer = new Customer();
            newCustomer.setUsername(user.getAttribute("email"));
            userRepository.save(newCustomer);
        }
        return user;
    }
}
