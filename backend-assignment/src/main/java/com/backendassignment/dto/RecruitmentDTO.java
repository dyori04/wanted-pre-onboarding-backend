package com.backendassignment.dto;
import com.backendassignment.entity.RecruitmentEntity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecruitmentDTO {
    private long id;
    private long companyId; // CompanyEntity의 ID 참조
    private String companyName; // to advertise recruitment using company name
    private String country;
    private String region;
    private String recruitPosition;
    private String recruitReward;
    private String techStack;
    private String recruitBody;

    public static RecruitmentDTO toCompanyDTO(RecruitmentEntity recruitmentEntity) {
        RecruitmentDTO recruitmentDTO = new RecruitmentDTO();
        recruitmentDTO.setId(recruitmentEntity.getId());
        recruitmentDTO.setCompanyId(recruitmentEntity.getCompany().getId()); 
        recruitmentDTO.setCountry(recruitmentEntity.getCountry());
        recruitmentDTO.setRegion(recruitmentEntity.getRegion());
        recruitmentDTO.setRecruitPosition(recruitmentEntity.getRecruitPosition());
        recruitmentDTO.setRecruitReward(recruitmentEntity.getRecruitReward());
        recruitmentDTO.setTechStack(recruitmentEntity.getTechStack());
        recruitmentDTO.setRecruitBody(recruitmentEntity.getRecruitBody());

        return recruitmentDTO;
    }
}
