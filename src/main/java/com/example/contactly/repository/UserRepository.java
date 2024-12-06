package com.example.contactly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.contactly.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
