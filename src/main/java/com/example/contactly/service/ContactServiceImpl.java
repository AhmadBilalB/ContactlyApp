package com.example.contactly.service;

import com.example.contactly.dto.ContactDTO;
import com.example.contactly.dto.UserDTO;
import com.example.contactly.entity.Contact;
import com.example.contactly.entity.User;
import com.example.contactly.enums.Role;
import com.example.contactly.exception.InvalidCredentialsException;
import com.example.contactly.exception.ResourceNotFoundException;
import com.example.contactly.mapper.ContactMapper;
import com.example.contactly.repository.ContactRepository;
import com.example.contactly.repository.UserRepository;
import com.example.contactly.security.CustomUserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    UserRepository userRepository;

    public ContactServiceImpl(ContactRepository contactRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    public ContactDTO save(ContactDTO contactDTO, CustomUserDetails userDetails) {

        UserDTO userDTO = userDetails.getUser(); // Moved user extraction here
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + userDTO.getEmail() + " not found"));

        Contact contact = ContactMapper.toEntity(contactDTO, user);
        contact = contactRepository.save(contact);
        return ContactMapper.toDTO(contact);

    }

    private List<ContactDTO> mapContactsToDTOs(List<Contact> contacts) {
        return contacts.stream()
                .map(ContactMapper::toDTO)
                .toList();
    }

    public List<ContactDTO> getUserContacts(CustomUserDetails userDetails) {

        UserDTO userDTO = userDetails.getUser(); // Moved user extraction here
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("User with email " + userDTO.getEmail() + " not found"));



// Check user role and fetch contacts accordingly
        List<Contact> contacts;
        if (user.getRole() == Role.ADMIN) {
            // Admin gets all contacts
            contacts = contactRepository.findAll();
        } else if (user.getRole() == Role.USER) {
            // User gets only their contacts
            contacts = contactRepository.findByUser(user);
        } else {
            throw new InvalidCredentialsException("Unauthorized role: " + user.getRole());
        }

// If no contacts are found, throw an exception
        if (contacts.isEmpty()) {
            throw new ResourceNotFoundException("No contacts found for user with email " + userDTO.getEmail());
        }

        return contacts.stream().map(ContactMapper::toDTO).toList();

    }
}
