package com.example.canteenmgmtsys.login.service;

import com.example.canteenmgmtsys.config.JwtService;
import com.example.canteenmgmtsys.customer.repo.CustomerRepo;
import com.example.canteenmgmtsys.login.dto.LoginRequestDto;
import com.example.canteenmgmtsys.login.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final CustomerRepo customerRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDto authenticate(LoginRequestDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        var user = customerRepo.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        var jwtToken = jwtService.generateToken(user);

        return LoginResponseDto.builder()
                .token(jwtToken)
                .build();
    }
}
