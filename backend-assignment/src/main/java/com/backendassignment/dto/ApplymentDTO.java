package com.backendassignment.dto;

import java.util.List;

import com.backendassignment.entity.ApplymentEntity;
import com.backendassignment.entity.RecruitmentEntity;
import com.backendassignment.entity.UserEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApplymentDTO {
    
    private Long userId;
    private Long recruitmentId;

    public static ApplymentDTO toApplymentDTO(ApplymentEntity applymentEntity){
        ApplymentDTO applymentDTO = new ApplymentDTO();
        applymentDTO.setUserId(applymentEntity.getUser().getId());
        applymentDTO.setRecruitmentId(applymentEntity.getRecruitment().getId());
        
        return applymentDTO;
    }
}
