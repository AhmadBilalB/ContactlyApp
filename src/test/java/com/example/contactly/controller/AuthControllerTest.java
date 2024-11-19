package com.example.contactly.controller;

import com.example.contactly.dto.ErrorResponseDTO;
import com.example.contactly.dto.LoginRequest;
import com.example.contactly.dto.LoginResponse;
import com.example.contactly.dto.UserDTO;
import com.example.contactly.entity.User;
import com.example.contactly.enums.Role;
import com.example.contactly.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.util.Objects;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;




@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;


    @Test
    public void testRegisterSuccess() throws Exception {
        // Prepare the UserDTO
        UserDTO userDTO = new UserDTO("test@example.com", "password", "1234567890", String.valueOf(Role.USER));

        // Create a mock User entity to be returned after registration
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPhoneNumber("1234567890");
        mockUser.setPassword("password");
        mockUser.setRole(Role.USER);

        // Mock service layer behavior to simulate success
        when(userService.getUserByEmail(userDTO.getEmail())).thenReturn(null); // Email not found
        when(userService.getUserByPhoneNumber(userDTO.getPhoneNumber())).thenReturn(false); // Phone number not found
        when(userService.registerUser(userDTO)).thenReturn(mockUser); // Successful user registration

        // Perform the registration call
        ResponseEntity<?> response = authController.register(userDTO);

        // Assert that the response status is 200 OK
        assertThat(response.getStatusCode().value()).isEqualTo(200);

        // Optionally, assert the response body is what you expect
        // For example, checking if the UserDTO is returned correctly (if applicable)
        assertThat(response.getBody()).isInstanceOf(UserDTO.class);
    }

    @Test
    public void testRegisterEmailAlreadyExists() throws Exception {
        // Prepare the UserDTO with an existing email
        UserDTO userDTO = new UserDTO("existing@example.com", "password", "1234567891", String.valueOf(Role.USER));

        // Create a mock User entity to simulate the existing user with the same email
        UserDTO existingUser = new UserDTO();
        existingUser.setEmail("existing@example.com");
        existingUser.setPhoneNumber("1234567890");
        existingUser.setPassword("password");
        existingUser.setRole(String.valueOf(Role.USER));

        // Mock service layer behavior to simulate email already exists
        when(userService.getUserByEmail(userDTO.getEmail())).thenReturn(existingUser);  // Simulating that the email already exists

        // Perform the registration call
        ResponseEntity<?> response = authController.register(userDTO);

        // Assert that the response status is 400 Bad Request
        assertThat(response.getStatusCode().value()).isEqualTo(400);

        // Assert that the error message contains the expected response
        if (response.getBody() instanceof ErrorResponseDTO errorResponse) {
            assertThat(errorResponse.getMessage()).isNotNull(); // Ensure it's not null
            assertThat(errorResponse.getMessage()).isEqualTo("Email already exists");
        }
    }

    @Test
    public void testRegisterPhoneNumberAlreadyExists() throws Exception {
        // Prepare the UserDTO with an existing phone number
        UserDTO userDTO = new UserDTO("existing@example.com", "password", "1234567890", String.valueOf(Role.USER));

        // Mock service layer behavior to simulate phone number already exists
        when(userService.getUserByPhoneNumber(userDTO.getPhoneNumber())).thenReturn(true); // Phone number already exists

        // Perform the registration call
        ResponseEntity<?> response = authController.register(userDTO);

        // Assert that the response status is 400 Bad Request
        assertThat(response.getStatusCode().value()).isEqualTo(400);

        // Assert that the error message contains the expected response
        assertThat(((ErrorResponseDTO) Objects.requireNonNull(response.getBody())).getMessage()).isEqualTo("Phone number already exists");
    }

    @Test
    public void testRegisterFailure() throws Exception {
        // Prepare the UserDTO
        UserDTO userDTO = new UserDTO("test@example.com", "password", "1234567890", String.valueOf(Role.USER));

        // Mock service layer behavior to simulate failure during registration
        when(userService.getUserByEmail(userDTO.getEmail())).thenReturn(null); // Email not found
        when(userService.getUserByPhoneNumber(userDTO.getPhoneNumber())).thenReturn(false); // Phone number not found
        when(userService.registerUser(userDTO)).thenThrow(new RuntimeException("Registration failed")); // Simulate failure

        // Perform the registration call
        ResponseEntity<?> response = authController.register(userDTO);

        // Assert that the response status is 400 Bad Request
        assertThat(response.getStatusCode().value()).isEqualTo(400);

        // Assert that the error message is what we expect
        assertThat(((ErrorResponseDTO) Objects.requireNonNull(response.getBody())).getMessage()).isEqualTo("Registration failed");
    }

    @Test
    public void testLoginSuccess() throws Exception {
        // Prepare the LoginRequest with valid credentials
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");

        // Create a mock LoginResponse (JWT token)
        LoginResponse loginResponse = new LoginResponse("valid-jwt-token");

        // Mock service layer behavior to simulate successful login
        when(userService.login(loginRequest)).thenReturn(loginResponse);

        // Perform the login call
        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        // Assert that the response status is 200 OK
        assertThat(response.getStatusCode().value()).isEqualTo(200);

        // Assert that the response contains the expected JWT token
        assertThat(Objects.requireNonNull(response.getBody()).getToken()).isEqualTo("valid-jwt-token");
    }

    @Test
    public void testLoginInvalidCredentials() throws Exception {
        // Prepare the LoginRequest with invalid credentials
        LoginRequest loginRequest = new LoginRequest("test@example.com", "wrong-password");

        // Leniently mock the behavior of authenticationManager to throw an AuthenticationException
        lenient().when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid email or password") {});

        // Perform the login call
        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        // Assert that the response status is 401 Unauthorized
        assertThat(response.getStatusCode().value()).isEqualTo(401);
    }

}
