package com.example.contactly.service;

import com.example.contactly.dto.LoginRequest;
import com.example.contactly.dto.LoginResponse;
import com.example.contactly.dto.UserDTO;
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



    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public User registerUser(UserDTO userDTO) throws Exception{
        User user = UserMapper.toEntity(userDTO);  // Convert DTO to Entity

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
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
    }
}
