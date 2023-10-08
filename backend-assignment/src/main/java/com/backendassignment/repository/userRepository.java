package com.backendassignment.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backendassignment.entity.UserEntity;

@Repository
public interface userRepository extends JpaRepository<UserEntity, Long>
{
    
}
