package com.example.vebibeer_be.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.vebibeer_be.model.entities.User;
import com.example.vebibeer_be.model.entities.BusCompany.BusCompany;
import com.example.vebibeer_be.model.entities.Customer.Customer;
import com.example.vebibeer_be.model.repo.BusCompanyRepo.BusCompanyRepo;
import com.example.vebibeer_be.model.repo.CustomerRepo.CustomerRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepo userRepository;

    @Autowired
    BusCompanyRepo busCompanyRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(email).orElse(null);
                // .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
            if (user == null) {
                user = busCompanyRepo.findByUsername(email);
            }
            if (user == null) {
                throw new UsernameNotFoundException("User not found with email : " + email);
            }
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserBusById(Integer id) {
        BusCompany busCompany = busCompanyRepo.getReferenceById(id);
        return UserPrincipal.create(busCompany);
    }

    @Transactional
    public UserDetails loadUserById(Integer id) {
        Customer user = userRepository.getReferenceById(id);
        return UserPrincipal.create(user);
    }

}