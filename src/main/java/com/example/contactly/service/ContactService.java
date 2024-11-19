package com.example.contactly.service;

import com.example.contactly.dto.ContactDTO;
import com.example.contactly.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {
    ContactDTO save(ContactDTO contactDTO, UserDTO userDTO);
    List<ContactDTO> getUserContacts(UserDTO userDTO);
}
