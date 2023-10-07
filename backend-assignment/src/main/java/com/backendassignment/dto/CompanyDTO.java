package com.backendassignment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.backendassignment.entity.CompanyEntity;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CompanyDTO {
    private long id;
    private String companyName;    

    public static CompanyDTO toCompanyDTO(CompanyEntity companyEntity){
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.setId(companyEntity.getId());
            companyDTO.setCompanyName(companyEntity.getCompanyName());

            return companyDTO;
    }

}
  