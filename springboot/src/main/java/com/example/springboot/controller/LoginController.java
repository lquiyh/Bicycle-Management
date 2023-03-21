package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.model.DTO.LoginDTO;
import com.example.springboot.model.DTO.UserRequest;
import com.example.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    Result login(@RequestBody UserRequest userRequest) {
        //System.out.println(userRequest.getUsername());
        LoginDTO loginDTO = loginService.login(userRequest);
        return Result.success(loginDTO);
    }

}
