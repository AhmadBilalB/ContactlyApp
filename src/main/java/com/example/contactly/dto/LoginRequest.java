package com.example.contactly.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {


    @Schema(description = "The email address of the user", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "The password for the user account", example = "StrongPassword123!", required = true)
    private String password;
}
