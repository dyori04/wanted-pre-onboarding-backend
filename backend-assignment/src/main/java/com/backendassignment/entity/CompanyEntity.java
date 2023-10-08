package com.backendassignment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

import com.backendassignment.dto.CompanyDTO;
import com.backendassignment.entity.RecruitmentEntity;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "company_table")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String companyName;

    @OneToMany(mappedBy ="company", cascade = CascadeType.REMOVE)
    private List<RecruitmentEntity> recruits;

    public static CompanyEntity toCompanyEntity(CompanyDTO companyDTO){
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(companyDTO.getId());
        companyEntity.setCompanyName(companyDTO.getCompanyName());
        return companyEntity;
    }

}
