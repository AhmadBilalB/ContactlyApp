package com.example.contactly.controller;

import com.example.contactly.dto.ContactDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;



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
            description = "Create a new contact with the provided details for the authenticated user and return the created contact in the response body."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json"))
    })
    @Parameter(
            name = "Authorization",
            description = "Bearer token for authentication",
            required = true,
            in = ParameterIn.HEADER
    )
    public ResponseEntity<ContactDTO> createContact(@RequestBody(
            description = "Details of the contact to be created",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))
    ) ContactDTO contactDTO,@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserDTO user = userDetails.getUser();

        if(user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        ContactDTO savedContactDTO = contactService.save(contactDTO, user);
        return ResponseEntity.ok(savedContactDTO);
    }

    @GetMapping
    @Operation(summary = "Get all contacts", description = "Retrieve a list of all contacts for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No contacts found")
    })
    @Parameter(name = "Authorization", description = "Bearer token for authentication", required = true, in = ParameterIn.HEADER)
    public ResponseEntity<List<ContactDTO>> getContacts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserDTO user = userDetails.getUser();

        if(user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<ContactDTO> contacts = contactService.getUserContacts(user);
        return ResponseEntity.ok(contacts);
    }
}
