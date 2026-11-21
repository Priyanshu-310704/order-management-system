package com.prysh.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserServiceController {
    @GetMapping("/health")
    public String health(){
        return "User service is Up and Running ✅";
    }
}
