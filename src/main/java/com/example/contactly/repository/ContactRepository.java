package com.example.contactly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.contactly.entity.Contact;
import com.example.contactly.entity.User;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByUser(User user);
}
