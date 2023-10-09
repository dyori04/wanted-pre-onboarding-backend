package com.backendassignment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

import com.backendassignment.dto.UserDTO;
import com.backendassignment.service.UserService;

@Controller
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody UserDTO userDTO) {
        
        System.out.println("requested User DTO = " + userDTO);
        try {
            userService.save(userDTO);
            return new ResponseEntity<>("requested User Name = " + userDTO.getUserId(),HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
