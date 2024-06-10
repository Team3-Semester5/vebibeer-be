package com.example.vebibeer_be.model.entities.BusCompany;
import lombok.*;

import java.time.LocalDateTime;

import com.example.vebibeer_be.model.entities.Role;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "buscompany")
public class BusCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int busCompany_id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    
    private String busCompany_status;

    
    private String busCompany_fullname;

   
    private String busCompany_dob;

    
    private String busCompany_imgUrl;

    private String busCompany_description;

    
    private String busCompany_nationally;

    
    private String busCompany_name;

    private String busCompany_location;

    private String busCompany_contract;

    
    private boolean enabled;

    
    private String verificationCode;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

     @Column(nullable = false)
    private LocalDateTime expirationTime;
}
