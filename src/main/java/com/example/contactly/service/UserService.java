package com.example.contactly.service;

import com.example.contactly.dto.LoginRequest;
import com.example.contactly.dto.LoginResponse;
import com.example.contactly.dto.UserDTO;
import com.example.contactly.entity.User;
import com.example.contactly.repository.UserRepository;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDTO registerUser(UserDTO userDTO) throws Exception;
    UserDTO getUserByEmail(String email);
    boolean getUserByPhoneNumber(@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits") String phoneNumber);
    LoginResponse login(LoginRequest loginRequest) throws Exception;
}
