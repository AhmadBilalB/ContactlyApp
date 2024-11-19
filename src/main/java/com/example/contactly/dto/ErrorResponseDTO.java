package com.example.contactly.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDTO {

    @Schema(description = "Error message detailing the issue", example = "Email already exists")
    private String message;

}
