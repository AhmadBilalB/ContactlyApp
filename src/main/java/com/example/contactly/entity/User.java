package com.example.contactly.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.envers.Audited;

import com.example.contactly.enums.Role;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`user`")
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Contact> contacts;


    public User( String mail, String password, String phoneNumber, Role role) {
        this.email = mail;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
