package com.example.contactly.mapper;

import com.example.contactly.entity.Contact;
import com.example.contactly.dto.ContactDTO;
import com.example.contactly.entity.User;
import com.example.contactly.exception.JsonSerializationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContactMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();


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

    public static String toJson(ContactDTO contact) {
        try {
            return objectMapper.writeValueAsString(contact); // Serializes the Contact object to JSON
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("Failed to serialize ContactDTO to JSON", e);
        }
    }
}
