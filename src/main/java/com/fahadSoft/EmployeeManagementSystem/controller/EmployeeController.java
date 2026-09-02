package com.fahadSoft.EmployeeManagementSystem.controller;

import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmployeeAddRequestDTO;
import com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs.EmployeeAddResponseDTO;
import com.fahadSoft.EmployeeManagementSystem.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeAddResponseDTO> createEmployee(@Valid @RequestBody EmployeeAddRequestDTO requestDTO) {
        log.info("Received API request to create employee with email: {}", requestDTO.getEmail());

        EmployeeAddResponseDTO response = employeeService.saveEmployee(requestDTO);

        log.info("Successfully processed employee creation request for email: {}", response.getEmail());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
