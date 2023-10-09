package com.backendassignment.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendassignment.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    Optional<UserEntity> findByUserId(String UserId);
    
}
