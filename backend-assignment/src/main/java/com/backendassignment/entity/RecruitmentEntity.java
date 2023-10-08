package com.backendassignment.entity;

import com.backendassignment.dto.RecruitmentDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import com.backendassignment.entity.CompanyEntity;

@Entity
@Setter
@Getter
@Table(name = "recruitment_table")
public class RecruitmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "companyId", nullable = false)
     private CompanyEntity company;

     private String country;
     private String region;
     private String recruitPosition;
     private String recruitReward;
     private String techStack;
     private String recruitBody;

    public static RecruitmentEntity toRecruitmentEntity(RecruitmentDTO recruitmentDTO, CompanyEntity companyEntity) {

        RecruitmentEntity recruitmentEntity = new RecruitmentEntity();
        recruitmentEntity.setId(recruitmentDTO.getId());
        recruitmentEntity.setCompany(companyEntity);
        recruitmentEntity.setCountry(recruitmentDTO.getCountry());
        recruitmentEntity.setRegion(recruitmentDTO.getRegion());
        recruitmentEntity.setRecruitPosition(recruitmentDTO.getRecruitPosition());
        recruitmentEntity.setRecruitReward(recruitmentDTO.getRecruitReward());
        recruitmentEntity.setTechStack(recruitmentDTO.getTechStack());
        recruitmentEntity.setRecruitBody(recruitmentDTO.getRecruitBody());
        return recruitmentEntity;
    }
}
