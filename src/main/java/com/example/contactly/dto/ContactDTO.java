package com.example.contactly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {

    @Schema(description = "The full name of the contact", example = "John Doe")
    private String name;

    @Schema(description = "The phone number of the contact", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "The email address of the contact", example = "john.doe@example.com")
    private String email;

    @Schema(description = "The physical address of the contact", example = "123 Main St, Springfield, USA")
    private String address;

}
