package com.backendassignment.service;

import org.springframework.stereotype.Service;

import com.backendassignment.dto.CompanyDTO;
import com.backendassignment.entity.CompanyEntity;
import com.backendassignment.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public void save(CompanyDTO companyDTO){
        CompanyEntity companyEntity = CompanyEntity.toCompanyEntity(companyDTO);
        companyRepository.save(companyEntity);
    }
    
}
