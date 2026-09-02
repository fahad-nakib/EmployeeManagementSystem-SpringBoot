package com.fahadSoft.EmployeeManagementSystem.service.impl;


import com.fahadSoft.EmployeeManagementSystem.entity.Employee;
import com.fahadSoft.EmployeeManagementSystem.exception.EmailAlreadyExistsException;
import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmailRequestDto;
import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmployeeAddRequestDTO;
import com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs.EmployeeAddResponseDTO;
import com.fahadSoft.EmployeeManagementSystem.repository.EmployeeRepository;
import com.fahadSoft.EmployeeManagementSystem.service.EmailService;
import com.fahadSoft.EmployeeManagementSystem.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    @Transactional
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

        try {
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

            EmailRequestDto emailRequest = EmailRequestDto.builder()
                    .toEmail(responseDTO.getEmail())
                    .subject("Welcome to IdealSoftware Limited")
                    .messageBody(emailBody)
                    .build();

            emailService.sendSimpleEmail(emailRequest);
            log.info("Welcome email sent successfully to: {}", responseDTO.getEmail());

        } catch (MailException e) {
            log.error("Failed to send welcome email to {}: {}", responseDTO.getEmail(), e.getMessage(),e);
        }

        return responseDTO;
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
        java.security.SecureRandom random = new java.security.SecureRandom();

        // প্রতিটি টাইপ থেকে অন্তত একটি করে ক্যারেক্টার নেওয়া নিশ্চিত করা
        StringBuilder password = new StringBuilder();
        password.append(chars.charAt(random.nextInt(chars.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        // বাকি ৫টি ক্যারেক্টার সব মিলিয়ে মিক্স করে নেওয়া
        String allAllowed = chars + digits + specialChars;
        for (int i = 0; i < 5; i++) {
            password.append(allAllowed.charAt(random.nextInt(allAllowed.length())));
        }

        // ক্যারেক্টারগুলো ওলটপালট (Shuffle) করা যাতে সিকোয়েন্স বোঝা না যায়
        java.util.List<String> letters = java.util.Arrays.asList(password.toString().split(""));
        java.util.Collections.shuffle(letters);
        return String.join("", letters);
    }

}
