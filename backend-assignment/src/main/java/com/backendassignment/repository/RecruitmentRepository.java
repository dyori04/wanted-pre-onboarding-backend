package com.backendassignment.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.backendassignment.entity.RecruitmentEntity;

public interface RecruitmentRepository extends JpaRepository<RecruitmentEntity, Long> {
    
    
}
