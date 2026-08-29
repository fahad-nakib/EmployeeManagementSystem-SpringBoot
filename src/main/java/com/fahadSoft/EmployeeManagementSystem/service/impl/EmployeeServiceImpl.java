package com.fahadSoft.EmployeeManagementSystem.service.impl;


import com.fahadSoft.EmployeeManagementSystem.entity.Employee;
import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmployeeAddRequestDTO;
import com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs.EmployeeAddResponseDTO;
import com.fahadSoft.EmployeeManagementSystem.repository.EmployeeRepository;
import com.fahadSoft.EmployeeManagementSystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public EmployeeAddResponseDTO saveEmployee(EmployeeAddRequestDTO dto) {

        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email address is already in use!");
        }

        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setDesignation(dto.getDesignation());
        employee.setJoiningDate(dto.getJoiningDate());
        employee.setStatus(dto.getStatus());
        employee.setDepartmentId(dto.getDepartmentId());
        employee.setSalary(dto.getSalary());
        employee.setCreatedAt(LocalDate.now());

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToResponseDTO(savedEmployee);
    }

    private EmployeeAddResponseDTO convertToResponseDTO(Employee employee) {
        EmployeeAddResponseDTO responseDTO = new EmployeeAddResponseDTO();
        responseDTO.setId(employee.getId());

        String fullName = employee.getFirstName() + (employee.getLastName() != null ? " " + employee.getLastName() : "");
        responseDTO.setFullName(fullName);

        responseDTO.setEmail(employee.getEmail());
        responseDTO.setPassword(employee.getPassword());
        responseDTO.setPhoneNumber(employee.getPhoneNumber());
        responseDTO.setDesignation(employee.getDesignation());
        responseDTO.setJoiningDate(employee.getJoiningDate());
        responseDTO.setStatus(employee.getStatus());
        responseDTO.setDepartmentId(employee.getDepartmentId());
        responseDTO.setSalary(employee.getSalary());

        return responseDTO;
    }
}
