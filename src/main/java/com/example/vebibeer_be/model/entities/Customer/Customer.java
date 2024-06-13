package com.example.vebibeer_be.model.entities.Customer;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import com.example.vebibeer_be.model.entities.Role;
import com.example.vebibeer_be.model.entities.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "customer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    
    private String customer_status;

    
    private String customer_fullname;

    
    private LocalDate customer_dob;

    
    private String customer_img_ava;

    private String customer_description;

    
    private String customer_nationality;

    private String customer_gender;

    private boolean verify_purchased;

    private long point;

    
    private boolean enabled;

    
    private String verificationCode;

    @ManyToOne
    @JoinColumn(name = "typeCustomer_id", referencedColumnName = "typeCustomer_id")
    private TypeCustomer typeCustomer;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    @OneToMany(mappedBy = "customer")
    private Set<Transaction> transactions;

    
    private LocalDateTime expirationTime;
}