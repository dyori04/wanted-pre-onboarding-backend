package com.backendassignment.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.backendassignment.entity.CompanyEntity;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long>
{
    Optional<CompanyEntity> findByCompanyName(String CompanyName);
}
