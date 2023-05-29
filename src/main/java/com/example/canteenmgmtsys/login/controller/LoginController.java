package com.example.canteenmgmtsys.login.controller;

import com.example.canteenmgmtsys.login.dto.LoginRequestDto;
import com.example.canteenmgmtsys.login.dto.LoginResponseDto;
import com.example.canteenmgmtsys.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/authenticate")
    public LoginResponseDto authenticate(@RequestBody LoginRequestDto request) {
        return loginService.authenticate(request);
    }
}
