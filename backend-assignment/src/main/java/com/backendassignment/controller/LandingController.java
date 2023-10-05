package com.backendassignment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LandingController{

    @RequestMapping("/")
    public String index() {
        return "Landing page";
    }
}