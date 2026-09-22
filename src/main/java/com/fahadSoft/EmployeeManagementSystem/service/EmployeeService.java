package com.fahadSoft.EmployeeManagementSystem.service;

import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmployeeAddRequestDTO;
import com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs.EmployeeAddResponseDTO;
import com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeAddResponseDTO saveEmployee(EmployeeAddRequestDTO requestDTO);
    List<EmployeeResponseDTO> getAllEmployees();
}