package com.backendassignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.fasterxml.jackson.databind.ObjectMapper; // Object To Json
import com.backendassignment.controller.CompanyController;
import com.backendassignment.service.CompanyService;
import com.backendassignment.dto.CompanyDTO;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Test
    public void testRegistration() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO(); 
        companyDTO.setCompanyName("TestCompany");

        doNothing().when(companyService).save(companyDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(companyDTO);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/company/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("requested Company Name = " + companyDTO.getCompanyName()));
    }
}