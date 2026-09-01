package com.fahadSoft.EmployeeManagementSystem.controller;

import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmployeeAddRequestDTO;
import com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs.EmployeeAddResponseDTO;
import com.fahadSoft.EmployeeManagementSystem.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeAddResponseDTO> createEmployee(@Valid @RequestBody EmployeeAddRequestDTO requestDTO) {
        EmployeeAddResponseDTO response = employeeService.saveEmployee(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
