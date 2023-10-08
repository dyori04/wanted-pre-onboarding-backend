package com.backendassignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.BeanWrapperImpl;
import com.backendassignment.dto.CompanyDTO;
import com.backendassignment.dto.RecruitmentDTO;
import com.backendassignment.repository.CompanyRepository;
import com.backendassignment.repository.RecruitmentRepository;
import com.backendassignment.service.CompanyService;
import com.backendassignment.service.RecruitmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recruitment")
public class RecruitmentController {
    
    private final CompanyService companyService;
    private final RecruitmentService recruitmentService;
    
    //Recruitment Advertisement
    @PostMapping("/advertise")
    public ResponseEntity<String> recruitmentAdvertise(@RequestBody RecruitmentDTO recruitmentDTO) {
        
        try {
            System.out.println("requested Recruitment DTO = " + recruitmentDTO);
            recruitmentService.saveRecruitment(recruitmentDTO);
            return new ResponseEntity<>("company " + recruitmentDTO.getCompanyName()+"\'s recruitment saved", HttpStatus.CREATED); 
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/modify")
    public ResponseEntity<String> recuritmentModify(@RequestBody Map<String, Object> payload) {
        
        // to check unavailable fields
        Set<String> allowedFields = Set.of("id", "country", "region", "recruitPosition", "recruitReward", "techStack", "recruitBody", "companyName");
        Set<String> fieldsInRequest = payload.keySet();
        
        for (String field : fieldsInRequest) {
            if (!allowedFields.contains(field)) {
                return new ResponseEntity<>(
                    "Field [" + field + "] is not an allowed field.",
                    HttpStatus.BAD_REQUEST
                );
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        RecruitmentDTO recruitmentDTO = objectMapper.convertValue(payload, RecruitmentDTO.class);
    
        try {
            recruitmentService.modifyRecruitment(recruitmentDTO);
            return new ResponseEntity<>("Recruitment Updated successfully - recruit ID : " + recruitmentDTO.getId().toString(),HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove/{recruitmentId}")
    public ResponseEntity<String> recuritmentRemove(@PathVariable Long recruitmentId){
        try {
            recruitmentService.removeRecruitment(recruitmentId);
            return new ResponseEntity<>("Recuritment with ID \'" + recruitmentId + "\' was successfully removed", HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
