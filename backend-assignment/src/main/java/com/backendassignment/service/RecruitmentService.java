package com.backendassignment.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.backendassignment.dto.RecruitmentDTO;
import com.backendassignment.entity.RecruitmentEntity;
import com.backendassignment.entity.CompanyEntity;
import com.backendassignment.repository.CompanyRepository;
import com.backendassignment.repository.RecruitmentRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.Attribute;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        throw new IllegalArgumentException("Mismatched companyName ( original company name :" + 
        existingRecruitment.get().getCompany().getCompanyName() +
        ") Cannot update companyName through this method.");
        }

        RecruitmentEntity updatedRecuritment = RecruitmentEntity.toRecruitmentEntity(recruitmentDTO, existingRecruitment.get().getCompany());
        recruitmentRepository.save(updatedRecuritment);
    }

    public void removeRecruitment(Long recruitmentId){
        if (!recruitmentRepository.existsById(recruitmentId)) {
            throw new IllegalArgumentException("Recruitment with ID \'" + recruitmentId + "\' not exists");
        }
        recruitmentRepository.deleteById(recruitmentId);
    }
    
    public List<RecruitmentDTO> getAllRecruitmentsWithoutBody() {
        List<RecruitmentEntity> recruitments = recruitmentRepository.findAll();
        return recruitments.stream()
        .map(recruitment -> {
            RecruitmentDTO dto = RecruitmentDTO.toRecruitmentDTO(recruitment);
            dto.setRecruitBody(null);
            return dto;
        })
        .collect(Collectors.toList());
    }

    public List<RecruitmentDTO> searchRecruitments(String field, String value) {
        Map<String, String> params = new HashMap<>();
        params.put(field, value);
        Specification<RecruitmentEntity> spec = createSpecification(params);
        return recruitmentRepository.findAll(spec).stream()
            .map(RecruitmentDTO::toRecruitmentDTO)
            .collect(Collectors.toList());
    }

    private Specification<RecruitmentEntity> createSpecification(Map<String, String> params){
        
        return (Root<RecruitmentEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

            // Check if the entity has the provided field.
                try {
                // This checks if the field exists in the entity.
                    root.get(key);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("The field " + key + " doesn't exist.");
                }

            // Add the like condition for the field.
                predicates.add(cb.like(root.get(key), "%" + value + "%"));
            }

        return cq.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }

    public List<RecruitmentDTO> searchRecruitmentsForValueOnly(String value){
                Specification<RecruitmentEntity> spec = createSpecificationValueOnly(value);
        return recruitmentRepository.findAll(spec).stream()
            .map(RecruitmentDTO::toRecruitmentDTO)
            .collect(Collectors.toList());
    }

    private Specification<RecruitmentEntity> createSpecificationValueOnly(String value){
        return (Root<RecruitmentEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(value != null && !value.trim().isEmpty()) {
                for (Attribute<?, ?> attr : root.getModel().getAttributes()) { 
                    //for every attribute in RecruitmentEntity, add conditions that matched "like" value
                    String attributeName = attr.getName();
                    if (attr.getJavaType().equals(String.class)) {
                        predicates.add(cb.like(root.get(attributeName),"%" + value + "%"));
                    }
                }
            }

            return cq.where(predicates.toArray(new Predicate[0])).getRestriction();
            // Generate Query using conditions in predicates
        };
    }

}
