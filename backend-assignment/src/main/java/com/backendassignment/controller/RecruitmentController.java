package com.backendassignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.backendassignment.dto.CompanyDTO;
import com.backendassignment.dto.RecruitmentDTO;
import com.backendassignment.dto.RecruitmentDetailsResponse;
import com.backendassignment.repository.CompanyRepository;
import com.backendassignment.repository.RecruitmentRepository;
import com.backendassignment.service.CompanyService;
import com.backendassignment.service.RecruitmentService;
import com.backendassignment.service.ApplymentService;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

import javax.naming.NameNotFoundException;

import java.util.List;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recruitment")
public class RecruitmentController {
    
    private final CompanyService companyService;
    private final RecruitmentService recruitmentService;
    private final ApplymentService applymentService;

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

    @GetMapping("/listall")
    @JsonView(RecruitmentDTO.View.Public.class)
    public ResponseEntity<List<RecruitmentDTO>> listAllRecruitments(){
        List<RecruitmentDTO> recruitments = recruitmentService.getAllRecruitmentsWithoutBody();
        return new ResponseEntity<>(recruitments, HttpStatus.OK);
    }

    @GetMapping("/search")
    @JsonView(RecruitmentDTO.View.Public.class)
    public ResponseEntity<List<RecruitmentDTO>> searchRecruitments(@RequestParam(required =  false) String field, @RequestParam String value) {
        try {
            List<RecruitmentDTO> recruitments;
            if (field == null || field.trim().isEmpty()) {
                recruitments = recruitmentService.searchRecruitmentsForValueOnly(value);
            }
            else {
                recruitments =  recruitmentService.searchRecruitments(field, value);
            }
            return ResponseEntity.ok(recruitments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @GetMapping("/details")
    @JsonView(RecruitmentDTO.View.Internal.class)
    public ResponseEntity<RecruitmentDetailsResponse> getReruitmentDetails(@RequestParam long recruitmentId){
        try {
            RecruitmentDetailsResponse response = recruitmentService.getRecruitmentDetails(recruitmentId);
           return new ResponseEntity<RecruitmentDetailsResponse>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e)  {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/applyment")
    public ResponseEntity<String> applyForRecruitment(@RequestBody Map<String, Object> requestBody) {
        try{
            String userId = (String) requestBody.get("userId");
            Long recruitmentId = Long.valueOf(requestBody.get("recruitmentId").toString());
            
            applymentService.applyForRecruitment(userId, recruitmentId);
            return new ResponseEntity<>("Applyment for " + recruitmentId + " was succeeded, user " + userId,HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
