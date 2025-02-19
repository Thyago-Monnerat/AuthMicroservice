package com.auth.microservice.controllers;

import com.auth.microservice.dtos.UserLoginDTO;
import com.auth.microservice.dtos.UserRegisterDTO;
import com.auth.microservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //Login
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok().body("token: " + userService.login(userLoginDTO));

    }
    //Register
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userRegisterDTO){
        return ResponseEntity.ok().body(userService.register(userRegisterDTO));
    }

}
