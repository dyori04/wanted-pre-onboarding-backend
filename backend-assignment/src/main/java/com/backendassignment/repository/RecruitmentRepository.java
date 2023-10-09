package com.backendassignment.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.backendassignment.entity.CompanyEntity;
import com.backendassignment.entity.RecruitmentEntity;

@Repository
public interface RecruitmentRepository extends JpaRepository<RecruitmentEntity, Long>, JpaSpecificationExecutor<RecruitmentEntity> {
        List<RecruitmentEntity> findByCompany(CompanyEntity company);

}
