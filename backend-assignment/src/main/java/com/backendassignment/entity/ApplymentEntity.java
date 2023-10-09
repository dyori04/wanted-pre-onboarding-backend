package com.backendassignment.entity;

import com.backendassignment.dto.RecruitmentDTO;
import com.backendassignment.entity.RecruitmentEntity;
import com.backendassignment.entity.UserEntity;
import com.backendassignment.dto.ApplymentDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MapsId;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "applyment_table")
public class ApplymentEntity {

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class ApplymentId implements Serializable {
        private Long userAutoId;
        private Long recruitmentId;
    }

    @EmbeddedId
    private ApplymentId id;

    @MapsId("userAutoId")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, nullable = false, updatable = false)
    private UserEntity user;
    
    @MapsId("recruitmentId")
    @ManyToOne
    @JoinColumn(name = "recruitment_id", referencedColumnName = "id", insertable = false, nullable = false, updatable = false)
    private RecruitmentEntity recruitment;
    
        public static ApplymentEntity toApplymentEntity(ApplymentDTO applymentDTO, RecruitmentEntity recruitmentEntity, UserEntity userEntity) {

        ApplymentEntity applymentEntity = new ApplymentEntity();

        ApplymentId id = new ApplymentId();
        id.setUserAutoId(userEntity.getId());
        id.setRecruitmentId(recruitmentEntity.getId());

        applymentEntity.setId(id);
        applymentEntity.setRecruitment(recruitmentEntity);
        applymentEntity.setUser(userEntity);
        return applymentEntity;
    }
}
