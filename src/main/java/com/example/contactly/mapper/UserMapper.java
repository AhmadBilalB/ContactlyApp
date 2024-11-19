package com.example.contactly.mapper;

import com.example.contactly.dto.UserDTO;
import com.example.contactly.entity.User;
import com.example.contactly.enums.Role;

import java.util.Optional;

public class UserMapper {
	
    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .role(Role.valueOf(userDTO.getRole()))
                .build();
    }

    public static UserDTO toDTO(Optional<User> user) {
        // Check if the Optional contains a User object
        if (user.isPresent()) {
            return UserDTO.builder()
                    .email(user.get().getEmail())  // Unwrap the User object and get its properties
                    .phoneNumber(user.get().getPhoneNumber())
                    .password(user.get().getPassword())
                    .role(user.get().getRole().name())
                    .build();
        } else {
            // Handle the case where the Optional is empty
            throw new IllegalArgumentException("User not found");
        }
    }


}
