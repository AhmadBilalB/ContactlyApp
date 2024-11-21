package com.example.contactly.controller;

import com.example.contactly.dto.ContactDTO;
import com.example.contactly.dto.ErrorResponseDTO;
import com.example.contactly.dto.UserDTO;
import com.example.contactly.security.CustomUserDetails;
import com.example.contactly.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;



import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping("/contacts")
@Tag(name = "Contacts", description = "APIs for managing user contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new contact",
            description = "Create a new contact with the provided details for the authenticated user and return the created contact in the response body.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the contact to be created",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Contact created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
            }
    )
    @Parameter(
            name = "Authorization",
            description = "Bearer token for authentication",
            required = true,
            in = ParameterIn.HEADER
    )
    public ResponseEntity<ContactDTO> createContact(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Details of the contact to be created",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))
    ) @RequestBody ContactDTO contactDTO,@AuthenticationPrincipal CustomUserDetails userDetails) {

        ContactDTO savedContactDTO = contactService.save(contactDTO, userDetails); // Now passing userDetails to service
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContactDTO);

    }

    @GetMapping
    @Operation(summary = "Get all contacts", description = "Retrieve a list of all contacts for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, e.g., user is not authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No contacts found for the user",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Parameter(name = "Authorization", description = "Bearer token for authentication", required = true, in = ParameterIn.HEADER)
    public ResponseEntity<List<ContactDTO>> getContacts(@AuthenticationPrincipal CustomUserDetails userDetails) {

        List<ContactDTO> contacts = contactService.getUserContacts(userDetails); // Now passing userDetails to service
        return ResponseEntity.ok(contacts);
    }
}
