package com.backendassignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.fasterxml.jackson.databind.ObjectMapper; // Object To Json
import com.backendassignment.controller.UserController;
import com.backendassignment.service.UserService;
import com.backendassignment.dto.UserDTO;
import com.backendassignment.entity.UserEntity;
import com.backendassignment.repository.UserRepository;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void testRegistration() throws Exception {
        UserDTO userDTO = new UserDTO(); 
        userDTO.setUserId("testuser1");
        UserEntity userEntity = new UserEntity();
        
        when(userRepository.findByUserId(userDTO.getUserId())).thenReturn(Optional.of(userEntity));        
        doThrow(new IllegalArgumentException("User Id " + userDTO.getUserId() + " already exists"))
        .when(userService).save(any(UserDTO.class));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User Id " + userDTO.getUserId() + " already exists"));
    }

    @Test
    public void testRegistrationWithoutRedundant() throws Exception {
        
        UserDTO userDTO = new UserDTO(); 
        userDTO.setUserId("TestUserId");
        when(userRepository.findByUserId(userDTO.getUserId())).thenReturn(Optional.empty());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("requested User Name = " + userDTO.getUserId()));
    }
    
}
