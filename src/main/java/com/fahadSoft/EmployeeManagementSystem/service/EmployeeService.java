package com.fahadSoft.EmployeeManagementSystem.service;

import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmployeeAddRequestDTO;
import com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs.EmployeeAddResponseDTO;

public interface EmployeeService {
    EmployeeAddResponseDTO saveEmployee(EmployeeAddRequestDTO requestDTO);
}