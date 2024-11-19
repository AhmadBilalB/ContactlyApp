package com.example.contactly.dto;


import com.example.contactly.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {


    @Schema(description = "User's email address. This must be unique for each user.",example = "user@example.com")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "User's phone number. This must be unique for each user.", example = "1234567890")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Schema(description = "User's password", example = "StrongPassword123!")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Schema(description = "Role of the user (e.g., USER, ADMIN)", example = "USER")
    private String role;


}
