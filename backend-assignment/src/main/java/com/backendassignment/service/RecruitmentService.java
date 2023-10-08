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
import java.util.Optional;

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
    public void modifyRecruitment(RecruitmentDTO recruitmentDTO) {
        if(recruitmentDTO.getId() <= 0) {
            throw new IllegalArgumentException("Invalid Recruitment ID");
        }

        Optional<RecruitmentEntity> existingRecruitment = recruitmentRepository.findById(recruitmentDTO.getId());

        if(!existingRecruitment.isPresent()){
            throw new IllegalArgumentException("Recruitment not found");
        }

        if (recruitmentDTO.getCompanyName() != null && 
        !recruitmentDTO.getCompanyName().equals(existingRecruitment.get().getCompany().getCompanyName())) {
        throw new IllegalArgumentException("Mismatched companyName. Cannot update companyName through this method.");
        }
        
        RecruitmentEntity updatedRecuritment = RecruitmentEntity.toRecruitmentEntity(recruitmentDTO, existingRecruitment.get().getCompany());
        recruitmentRepository.save(updatedRecuritment);
    }

}
