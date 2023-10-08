package com.backendassignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import lombok.RequiredArgsConstructor;
import com.backendassignment.dto.CompanyDTO;
import com.backendassignment.dto.RecruitmentDTO;
import com.backendassignment.repository.CompanyRepository;
import com.backendassignment.repository.RecruitmentRepository;
import com.backendassignment.service.CompanyService;
import com.backendassignment.service.RecruitmentService;

@Controller
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/recruitment")
public class RecruitmentController {
    
    private final CompanyService companyService;
    private final RecruitmentService recruitmentService;
    
    //Recruitment Advertisement
    @PostMapping("/advertise")
    public String recruitmentAdvertise(@ModelAttribute RecruitmentDTO recruitmentDTO) {
        
        System.out.println("requested Recruitment DTO = " + recruitmentDTO);
        recruitmentService.saveRecruitment(recruitmentDTO);
        return "company " + recruitmentDTO.getCompanyName()+"recruitment saved"; 
    }
}
