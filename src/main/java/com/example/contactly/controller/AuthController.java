package com.example.contactly.controller;

import com.example.contactly.dto.*;
import com.example.contactly.entity.User;
import com.example.contactly.mapper.UserMapper;
import com.example.contactly.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import io.swagger.v3.oas.annotations.tags.Tag;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "APIs for user authentication and registration")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register new user
    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Register a new user by providing their email, phone number, and other details. Returns the created user or an error if registration fails.",
            requestBody = @RequestBody(
                    description = "Details of the user to register",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request, e.g., email or phone number already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
            }
    )
    public ResponseEntity<?> register(@RequestBody(
            description = "User registration details",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            ) UserDTO userDTO) {

        if (userService.getUserByEmail(userDTO.getEmail()) != null) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Email already exists"));
        }
        if (userService.getUserByPhoneNumber(userDTO.getPhoneNumber())) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Phone number already exists"));
        }
        try {
            User savedUser = userService.registerUser(userDTO);
            return ResponseEntity.ok(UserMapper.toDTO(Optional.ofNullable(savedUser)));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login a user",
            description = "Authenticate a user with their email and password. Returns a JWT token if authentication is successful.",
            requestBody = @RequestBody(
                    description = "Login credentials",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized, invalid email or password",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Unexpected server error")
            }
    )
    public ResponseEntity<LoginResponse> login(@RequestBody(
            description = "Login credentials",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequest.class))
    ) LoginRequest loginRequest) {
        try {
            LoginResponse jwtToken = userService.login(loginRequest);
            if(jwtToken == null) {
                return ResponseEntity.status(401).body(new LoginResponse("Invalid email or password"));
            }
            return ResponseEntity.ok(jwtToken);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new LoginResponse("Invalid email or password"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
