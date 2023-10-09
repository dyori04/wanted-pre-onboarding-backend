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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.fasterxml.jackson.databind.ObjectMapper; // Object To Json
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.EntityNotFoundException;

import com.backendassignment.dto.RecruitmentDTO;
import com.backendassignment.dto.RecruitmentDetailsResponse;
import com.backendassignment.dto.UserDTO;
import com.backendassignment.dto.CompanyDTO;
import com.backendassignment.dto.ApplymentDTO;

import com.backendassignment.entity.UserEntity;
import com.backendassignment.entity.RecruitmentEntity;
import com.backendassignment.entity.CompanyEntity;
import com.backendassignment.entity.ApplymentEntity;

import com.backendassignment.service.CompanyService;
import com.backendassignment.service.RecruitmentService;
import com.backendassignment.service.ApplymentService;

import com.backendassignment.repository.RecruitmentRepository;
import com.backendassignment.repository.CompanyRepository;
import com.backendassignment.repository.ApplymentRepository;
import com.backendassignment.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RecruitmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;
    @MockBean
    private RecruitmentService recruitmentService;
    @MockBean
    private ApplymentService applymentService;
    @MockBean
    private RecruitmentRepository recruitmentRepository;
    @MockBean
    private CompanyRepository companyRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ApplymentRepository applymentRepository;

    @Test
    public void testRecruitmentAdvertise() throws Exception{

        RecruitmentDTO recruitmentDTO = new RecruitmentDTO(); 
        CompanyEntity companyEntity = new CompanyEntity();

        companyEntity.setCompanyName("testcompany1");
        //recruitmentDTO.setCompanyId(companyEntity.getId());
        //recruitmentDTO.setCompanyName(companyEntity.getCompanyName());
        // Company not found Exception
        when(companyRepository.findByCompanyName(companyEntity.getCompanyName())).thenReturn(null);        
        doThrow(new EntityNotFoundException("Company not found with name: " + recruitmentDTO.getCompanyName() + ", Company Registration needed"))
        .when(recruitmentService).saveRecruitment(any(RecruitmentDTO.class));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(recruitmentDTO);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/recruitment/advertise")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Company not found with name: " + recruitmentDTO.getCompanyName() + ", Company Registration needed"));
        
        when(companyRepository.findByCompanyName(companyEntity.getCompanyName())).thenReturn(null);        
        doNothing().when(recruitmentService).saveRecruitment(any(RecruitmentDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
            .post("/recruitment/advertise")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated())
            .andExpect(content().string("company " + recruitmentDTO.getCompanyName()+"\'s recruitment saved"));
    }

    @Test
    public void testRecruitmentModify() throws Exception{
        
        RecruitmentDTO recruitmentDTO = new RecruitmentDTO(); 

        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyName("testcompany1");

        RecruitmentEntity recruitmentEntity = new RecruitmentEntity();
        recruitmentDTO.setId(-998L);
        recruitmentEntity.setCompany(companyEntity);

        doThrow(new IllegalArgumentException("Recruitment with ID \'" + recruitmentDTO.getId() + "\' not exists"))
        .when(recruitmentService).modifyRecruitment(any(RecruitmentDTO.class)); // wrong RecruitmentId Handling

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(recruitmentDTO);
        ((ObjectNode) jsonNode).remove("companyId");
        String json = objectMapper.writeValueAsString(jsonNode);

        mockMvc.perform(MockMvcRequestBuilders 
                .post("/recruitment/modify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Recruitment with ID \'" + recruitmentDTO.getId() + "\' not exists"));

        recruitmentDTO.setId(100L);
        recruitmentDTO.setCompanyName(companyEntity.getCompanyName());

        doThrow(new IllegalArgumentException("Mismatched companyName ( original company name :" + 
        recruitmentEntity.getCompany().getCompanyName() +
        ") Cannot update companyName through this method."))
        .when(recruitmentService).modifyRecruitment(any(RecruitmentDTO.class)); // diferent companyName between before_modified and after_modified

        jsonNode = objectMapper.valueToTree(recruitmentDTO);
        ((ObjectNode) jsonNode).remove("companyId");
        json = objectMapper.writeValueAsString(jsonNode);

        mockMvc.perform(MockMvcRequestBuilders 
                .post("/recruitment/modify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Mismatched companyName ( original company name :" + 
                    recruitmentDTO.getCompanyName() +
                    ") Cannot update companyName through this method."));
        
        when(companyRepository.findByCompanyName(companyEntity.getCompanyName())).thenReturn(null);        
        doNothing().when(recruitmentService).modifyRecruitment(any(RecruitmentDTO.class)); //Normal Response

        mockMvc.perform(MockMvcRequestBuilders 
            .post("/recruitment/modify")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated())
            .andExpect(content().string("Recruitment Updated successfully - recruit ID : " + recruitmentDTO.getId().toString()));
    
    }

    @Test
    public void testRecruitmentRemove_failure() throws Exception {
        Long invalidRecruitmentId = -1L;

        when(recruitmentRepository.existsById(invalidRecruitmentId)).thenReturn(false);
        doThrow(new IllegalArgumentException("Recruitment with ID '" + invalidRecruitmentId + "' not exists"))
                .when(recruitmentService).removeRecruitment(invalidRecruitmentId);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/recruitment/remove/" + invalidRecruitmentId))
                .andExpect(content().string("Recruitment with ID '" + invalidRecruitmentId + "' not exists"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRecruitmentRemove_successful() throws Exception {
        Long validRecruitmentId = 1L;

        // Do nothing when the recruitmentService.removeRecruitment() method is called
        when(recruitmentRepository.existsById(validRecruitmentId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/recruitment/remove/" + validRecruitmentId))
                .andExpect(content().string("Recruitment with ID '" + validRecruitmentId + "' was successfully removed"))
                .andExpect(status().isOk());
    }


    // @Test
    // public void testRecruitmentListall() throws Exception{

    // }

    // @Test
    // public void testRecruitmentSearch() throws Exception{

    // }

    // @Test
    // public void testRecruitmentDetails() throws Exception{

    // }

    // @Test
    // public void testRecruitmentApplyment() throws Exception{

    // }
}
