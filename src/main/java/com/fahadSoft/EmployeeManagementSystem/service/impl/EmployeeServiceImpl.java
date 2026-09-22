package com.fahadSoft.EmployeeManagementSystem.service.impl;

import com.fahadSoft.EmployeeManagementSystem.entity.Employee;
import com.fahadSoft.EmployeeManagementSystem.exception.EmailAlreadyExistsException;
import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmployeeAddRequestDTO;
import com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs.EmployeeAddResponseDTO;
import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmailMessageDto;
import com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs.EmployeeResponseDTO;
import com.fahadSoft.EmployeeManagementSystem.publisher.EmailPublisher;
import com.fahadSoft.EmployeeManagementSystem.repository.EmployeeRepository;
import com.fahadSoft.EmployeeManagementSystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailPublisher emailPublisher;

    @Override
    @Transactional
    @CacheEvict(value = "employees", allEntries = true) // Invalidate cached employee lists upon new insertion
    public EmployeeAddResponseDTO saveEmployee(EmployeeAddRequestDTO dto) {

        if (employeeRepository.existsByEmail(dto.getEmail())) {
            log.warn("Employee creation failed: Email address '{}' is already in use", dto.getEmail());
            throw new EmailAlreadyExistsException("Email address is already in use!");
        }

        String rawPassword = generateRandomPassword();

        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPassword(passwordEncoder.encode(rawPassword));
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setDesignation(dto.getDesignation());
        employee.setJoiningDate(dto.getJoiningDate());
        employee.setStatus(dto.getStatus());
        employee.setDepartmentId(dto.getDepartmentId());
        employee.setSalary(dto.getSalary());
        employee.setCreatedAt(LocalDate.now());

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee successfully saved in database with Generated ID: {}", savedEmployee.getId());

        EmployeeAddResponseDTO responseDTO = convertToResponseDTO(savedEmployee);

        String emailBody = String.format(
                """
                        Hello %s,
                        
                        Your employee account has been created successfully.
                        Here are your login credentials:
                        Email: %s
                        Temporary Password: %s
                        
                        Please change your password after your first login.
                        
                        Best Regards,
                        IdealSoftware Limited""",
                responseDTO.getFullName(), responseDTO.getEmail(), rawPassword
        );

        EmailMessageDto emailMessage = EmailMessageDto.builder()
                .toEmail(responseDTO.getEmail())
                .subject("Welcome to IdealSoftware Limited")
                .messageBody(emailBody)
                .build();

        // Send RabbitMQ Event ONLY after DB Transaction Commits successfully
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    emailPublisher.sendEmailEvent(emailMessage);
                }
            });
        } else {
            emailPublisher.sendEmailEvent(emailMessage);
        }

        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "employees")
    public List<EmployeeResponseDTO> getAllEmployees() {
        log.info("Fetching all employees from database...");

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(this::convertToEmployeeResponseDTO)
                .collect(Collectors.toList());
    }

    private EmployeeResponseDTO convertToEmployeeResponseDTO(Employee employee) {
        String fullName = employee.getFirstName()
                + (employee.getLastName() != null && !employee.getLastName().isBlank() ? " " + employee.getLastName() : "");

        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .fullName(fullName.trim())
                .email(employee.getEmail())
                .designation(employee.getDesignation())
                .role(employee.getRole())
                .status(employee.getStatus())
                .build();
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

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "@$!%*?&";
        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();
        password.append(chars.charAt(random.nextInt(chars.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        String allAllowed = chars + digits + specialChars;
        for (int i = 0; i < 5; i++) {
            password.append(allAllowed.charAt(random.nextInt(allAllowed.length())));
        }

        List<String> letters = Arrays.asList(password.toString().split(""));
        Collections.shuffle(letters);
        return String.join("", letters);
    }
}