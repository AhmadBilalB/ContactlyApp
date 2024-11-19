package com.example.contactly.mapper;

import com.example.contactly.entity.Contact;
import com.example.contactly.dto.ContactDTO;
import com.example.contactly.entity.User;

public class ContactMapper {

    public static Contact toEntity(ContactDTO contactDTO, User user) {
        return Contact.builder()
                .name(contactDTO.getName())
                .phoneNumber(contactDTO.getPhoneNumber())
                .email(contactDTO.getEmail())
                .address(contactDTO.getAddress())
                .user(user)
                .build();
    }

    public static ContactDTO toDTO(Contact contact) {
        return ContactDTO.builder()
                .name(contact.getName())
                .phoneNumber(contact.getPhoneNumber())
                .email(contact.getEmail())
                .address(contact.getAddress())
                .build();
    }
}
