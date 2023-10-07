package com.backendassignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;

import com.backendassignment.dto.CompanyDTO;
import com.backendassignment.repository.CompanyRepository;
import com.backendassignment.service.CompanyService;

@Controller
@RequiredArgsConstructor
@ResponseBody
public class CompanyController {

    private final CompanyService companyService;

    // Company Information registration
    @PostMapping("/company/registration")
    public String registration(@ModelAttribute CompanyDTO companyDTO) {
        System.out.println("requested Company DTO = " + companyDTO);
        companyService.save(companyDTO);
        return "requested Company Name = " + companyDTO.getCompanyName();
    }
}