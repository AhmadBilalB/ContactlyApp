package com.example.contactly.controller;

import com.example.contactly.dto.ContactDTO;
import com.example.contactly.dto.UserDTO;
import com.example.contactly.security.CustomUserDetails;
import com.example.contactly.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ContactControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    private CustomUserDetails customUserDetails;
    private UserDTO userDTO;
    private ContactDTO contactDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();

        // Setup mock user
        userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setRole("USER");

        customUserDetails = new CustomUserDetails(userDTO);

        // Setup a mock contact
        contactDTO = new ContactDTO();
        contactDTO.setName("John Doe");
        contactDTO.setPhoneNumber("123-456-7890");
    }

//    @Test
//    @WithMockUser(username = "test@example.com", roles = "USER")
//    void testCreateContactSuccess() throws Exception {
//        when(contactService.save(Mockito.any(ContactDTO.class), Mockito.any(UserDTO.class))).thenReturn(contactDTO);
//
//        mockMvc.perform(post("/contacts")
//                        .contentType("application/json")
//                        .content("{ \"name\": \"John Doe\", \"phoneNumber\": \"123-456-7890\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("John Doe"))
//                .andExpect(jsonPath("$.phoneNumber").value("123-456-7890"));
//    }


    @Test
    void testCreateContactBadRequestNoUser() throws Exception {
        CustomUserDetails emptyUserDetails = new CustomUserDetails(null);

        // Mock Principal to return null user
        Principal principal = () -> emptyUserDetails.getUsername();

        mockMvc.perform(post("/contacts")
                        .contentType("application/json")
                        .content("{ \"name\": \"John Doe\", \"phoneNumber\": \"123-456-7890\"}")
                        .principal(principal))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    void testGetContactsSuccess() throws Exception {
//        ContactDTO contact1 = new ContactDTO();
//        contact1.setName("John Doe");
//        contact1.setPhoneNumber("123-456-7890");
//
//        ContactDTO contact2 = new ContactDTO();
//        contact2.setName("Jane Smith");
//        contact2.setPhoneNumber("098-765-4321");
//
//        List<ContactDTO> contacts = Arrays.asList(contact1, contact2);
//
//        when(contactService.getUserContacts(Mockito.any(UserDTO.class))).thenReturn(contacts);
//
//        // Mock Principal to return CustomUserDetails
//        Principal principal = () -> customUserDetails.getUsername();
//
//        mockMvc.perform(get("/contacts")
//                        .principal(principal))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("John Doe"))
//                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
//    }

    @Test
    void testGetContactsBadRequestNoUser() throws Exception {
        CustomUserDetails emptyUserDetails = new CustomUserDetails(null);

        // Mock Principal to return null user
        Principal principal = () -> emptyUserDetails.getUsername();

        mockMvc.perform(get("/contacts")
                        .principal(principal))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateContactBadRequest() throws Exception {
        mockMvc.perform(post("/contacts")
                        .contentType("application/json")
                        .content("{ \"name\": \"\" , \"phoneNumber\": \"\"}")
                        .principal(() -> "test@example.com"))
                .andExpect(status().isBadRequest());
    }
}
