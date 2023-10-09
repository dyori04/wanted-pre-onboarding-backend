package com.backendassignment.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.backendassignment.entity.ApplymentEntity;
import java.util.Optional;
@Repository
public interface ApplymentRepository extends JpaRepository<ApplymentEntity, ApplymentEntity.ApplymentId> {

}

