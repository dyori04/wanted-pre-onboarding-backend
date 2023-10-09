package com.backendassignment.service;

import org.springframework.stereotype.Service;
import com.backendassignment.dto.ApplymentDTO;
import com.backendassignment.entity.ApplymentEntity;
import com.backendassignment.entity.RecruitmentEntity;
import com.backendassignment.entity.UserEntity;
import com.backendassignment.repository.ApplymentRepository;
import com.backendassignment.repository.UserRepository;
import com.backendassignment.repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplymentService {

    private final UserRepository userRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ApplymentRepository applymentRepository;

    public void applyForRecruitment(String userId, Long recruitmentId){

        Optional<UserEntity> vaild_user = userRepository.findByUserId(userId);
        Optional<RecruitmentEntity>  vaild_Recruitment = recruitmentRepository.findById(recruitmentId);
        
        if(!vaild_Recruitment.isPresent()) {
            throw new IllegalArgumentException("recruit Id " + recruitmentId + " does not exists");
        }
        if (!vaild_user.isPresent()){
            throw new IllegalArgumentException("User " + userId + " does not exists");
        }

        ApplymentEntity.ApplymentId applymentId = new ApplymentEntity.ApplymentId();
        applymentId.setUserAutoId(vaild_user.get().getId());
        applymentId.setRecruitmentId(recruitmentId);

        if(applymentRepository.existsById(applymentId)){
            throw new IllegalArgumentException("User " + userId + " already applied in recruitement Id : " + recruitmentId);
        }

        ApplymentEntity newApplyment = new ApplymentEntity();
        newApplyment.setId(applymentId);
        newApplyment.setUser(vaild_user.get());
        newApplyment.setRecruitment(vaild_Recruitment.get());

        applymentRepository.save(newApplyment);
    }
}
