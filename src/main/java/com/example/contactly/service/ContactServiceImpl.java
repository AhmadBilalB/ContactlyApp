package com.example.contactly.service;

import com.example.contactly.dto.ContactDTO;
import com.example.contactly.dto.UserDTO;
import com.example.contactly.entity.Contact;
import com.example.contactly.entity.User;
import com.example.contactly.enums.Role;
import com.example.contactly.mapper.ContactMapper;
import com.example.contactly.repository.ContactRepository;
import com.example.contactly.repository.UserRepository;
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

    public ContactDTO save(ContactDTO contactDTO, UserDTO userDTO) {

        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if(user.isPresent()) {
            Contact contact = ContactMapper.toEntity(contactDTO, user.get());  // Convert DTO to Entity
            contact = contactRepository.save(contact);
            return ContactMapper.toDTO(contact);
        }else {
            return null;
        }
    }

    private List<ContactDTO> mapContactsToDTOs(List<Contact> contacts) {
        return contacts.stream()
                .map(ContactMapper::toDTO)
                .toList();
    }

    public List<ContactDTO> getUserContacts(UserDTO userDTO) {

        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if(user.isPresent()) {
            if (user.get().getRole() == Role.ADMIN) {
                return mapContactsToDTOs(contactRepository.findAll());
            } else {
                return mapContactsToDTOs(contactRepository.findByUser(user.get()));
            }
        }else {
            return new ArrayList<>() {};
        }
    }
}
