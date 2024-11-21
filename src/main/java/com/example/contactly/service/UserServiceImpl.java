package com.example.contactly.service;

import com.example.contactly.dto.LoginRequest;
import com.example.contactly.dto.LoginResponse;
import com.example.contactly.dto.UserDTO;
import com.example.contactly.exception.EmailAlreadyExistsException;
import com.example.contactly.exception.PhoneNumberAlreadyExistsException;
import com.example.contactly.exception.UserNotFoundException;
import com.example.contactly.mapper.UserMapper;
import com.example.contactly.utils.JwtUtil;
import jakarta.validation.constraints.Pattern;

import com.example.contactly.entity.User;
import com.example.contactly.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;



    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();

    }

    public UserDTO registerUser(UserDTO userDTO) throws Exception{

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        if (userRepository.findByPhoneNumber(userDTO.getPhoneNumber()).isPresent()) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }

        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(Optional.of(savedUser));

    }


    public UserDTO getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return UserMapper.toDTO(user);
        } else {
            return null; // or you can return a custom error response
        }
    }

    public boolean getUserByPhoneNumber(@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits") String phoneNumber) {

        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);

        // or you can return a custom error response
        return user.isPresent();
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // If authentication is successful, generate JWT token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

            // Return the token in the response
            return new LoginResponse(jwtToken);

        }catch (Exception e) {
            throw new UserNotFoundException("Invalid email or password");
        }

    }
}
