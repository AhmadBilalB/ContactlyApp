package com.example.contactly.service;

import com.example.contactly.entity.User;
import com.example.contactly.mapper.UserMapper;
import com.example.contactly.repository.UserRepository;
import com.example.contactly.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load the user by email (which is the username in your case)
        Optional<User> user = userRepository.findByEmail(username);  // Assuming your UserRepository has this method

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return new CustomUserDetails(UserMapper.toDTO(user));
        // Convert User entity to Spring Security UserDetails
    }
}
