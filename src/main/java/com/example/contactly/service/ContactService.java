package com.example.contactly.service;

import com.example.contactly.dto.ContactDTO;
import com.example.contactly.dto.UserDTO;
import com.example.contactly.security.CustomUserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {
    ContactDTO save(ContactDTO contactDTO, CustomUserDetails userDTO);
    List<ContactDTO> getUserContacts(CustomUserDetails userDTO);
}
