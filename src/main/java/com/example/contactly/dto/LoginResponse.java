package com.example.contactly.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    @Schema(description = "JWT token issued for the authenticated user", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.S5g-YHGyhvdfG8X7V2rpzLRvB-9UbYf9", required = true)
    private String token;

}
