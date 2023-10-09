package com.backendassignment.service;

import org.springframework.stereotype.Service;

import com.backendassignment.dto.UserDTO;
import com.backendassignment.entity.UserEntity;
import com.backendassignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;


    public void save(UserDTO userDTO){
        Optional<UserEntity> redundantchecker = userRepository.findByUserId(userDTO.getUserId());
        if (redundantchecker.isPresent()){
            throw new IllegalArgumentException("User Id " + userDTO.getUserId() + " already exists");
        }
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity);
    }    

}
