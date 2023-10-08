package com.backendassignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backendassignment.dto.RecruitmentDTO;
import com.backendassignment.entity.RecruitmentEntity;
import com.backendassignment.entity.CompanyEntity;
import com.backendassignment.repository.CompanyRepository;
import com.backendassignment.repository.RecruitmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentService {
    
    private final RecruitmentRepository recruitmentRepository;
    private final CompanyRepository companyRepository;

    public void saveRecruitment(RecruitmentDTO recruitmentDTO){

        CompanyEntity companyEntity = companyRepository.findByCompanyName(recruitmentDTO.getCompanyName()) 
        .orElseThrow(() -> new EntityNotFoundException("Company not found with name: " + recruitmentDTO.getCompanyName() + ", Company Registration needed")); //company Entity Generate

        RecruitmentEntity recruitmentEntity = RecruitmentEntity.toRecruitmentEntity(recruitmentDTO, companyEntity);
        
        recruitmentRepository.save(recruitmentEntity);
    }

}
