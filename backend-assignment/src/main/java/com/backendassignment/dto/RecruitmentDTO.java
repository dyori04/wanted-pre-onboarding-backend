package com.backendassignment.dto;
import java.util.List;

import com.backendassignment.entity.RecruitmentEntity;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecruitmentDTO {

    public class View {
        public static class Public {}
        public static class Internal extends Public {}
    }

    @JsonView(View.Public.class)
    private Long id;

    @JsonView(View.Public.class)
    private Long companyId; // CompanyEntity의 ID 참조

    @JsonView(View.Public.class)
    private String companyName; // to advertise recruitment using company name
    
    @JsonView(View.Public.class)
    private String country;
    
    @JsonView(View.Public.class)
    private String region;
    
    @JsonView(View.Public.class)
    private String recruitPosition;
    
    @JsonView(View.Public.class)
    private String recruitReward;
    
    @JsonView(View.Public.class)
    private String techStack;
    
    @JsonView(View.Internal.class)
    private String recruitBody;

    public static RecruitmentDTO toRecruitmentDTO(RecruitmentEntity recruitmentEntity) {
        RecruitmentDTO recruitmentDTO = new RecruitmentDTO();
        recruitmentDTO.setId(recruitmentEntity.getId());
        recruitmentDTO.setCompanyId(recruitmentEntity.getCompany().getId()); 
        recruitmentDTO.setCompanyName(recruitmentEntity.getCompany().getCompanyName());
        recruitmentDTO.setCountry(recruitmentEntity.getCountry());
        recruitmentDTO.setRegion(recruitmentEntity.getRegion());
        recruitmentDTO.setRecruitPosition(recruitmentEntity.getRecruitPosition());
        recruitmentDTO.setRecruitReward(recruitmentEntity.getRecruitReward());
        recruitmentDTO.setTechStack(recruitmentEntity.getTechStack());
        recruitmentDTO.setRecruitBody(recruitmentEntity.getRecruitBody());

        return recruitmentDTO;
    }
}
