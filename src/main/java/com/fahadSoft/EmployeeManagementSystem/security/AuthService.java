package com.fahadSoft.EmployeeManagementSystem.security;

import com.fahadSoft.EmployeeManagementSystem.entity.Employee;
import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.LoginRequestDTO;
import com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),loginRequestDTO.getPassword())
        );

        Employee employee = (Employee) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(employee);

        return LoginResponseDTO.builder()
                .accessToken(token)
                .id(employee.getId())
                .email(employee.getEmail())
                .firstName(employee.getFirstName())
                .designation(employee.getDesignation())
                .build();
    }
}
