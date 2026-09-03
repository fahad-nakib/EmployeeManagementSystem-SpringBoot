package com.fahadSoft.EmployeeManagementSystem.service;


import com.fahadSoft.EmployeeManagementSystem.entity.Employee;
import com.fahadSoft.EmployeeManagementSystem.entity.enums.StatusEnum;
import com.fahadSoft.EmployeeManagementSystem.exception.EmailAlreadyExistsException;
import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmailRequestDto;
import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmployeeAddRequestDTO;
import com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs.EmployeeAddResponseDTO;
import com.fahadSoft.EmployeeManagementSystem.repository.EmployeeRepository;
import com.fahadSoft.EmployeeManagementSystem.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeServiceImpl Unit Tests")
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor;

    @Captor
    private ArgumentCaptor<EmailRequestDto> emailRequestCaptor;

    private EmployeeAddRequestDTO requestDTO;
    private Employee savedEmployee;

    @BeforeEach
    void setUp() {
        requestDTO = new EmployeeAddRequestDTO();
        requestDTO.setFirstName("Fahad");
        requestDTO.setLastName("Nakib");
        requestDTO.setEmail("fahad@idealsoftware.com");
        requestDTO.setPhoneNumber("01700000000");
        requestDTO.setDesignation("Software Engineer");
        requestDTO.setJoiningDate(LocalDate.of(2026, 4, 1));
        requestDTO.setStatus(StatusEnum.ACTIVE);
        requestDTO.setDepartmentId(101L);
        requestDTO.setSalary(Double.valueOf(50000));

        savedEmployee = new Employee();
        savedEmployee.setId(1L);
        savedEmployee.setFirstName("Fahad");
        savedEmployee.setLastName("Nakib");
        savedEmployee.setEmail("fahad@idealsoftware.com");
        savedEmployee.setPassword("encoded_random_password");
        savedEmployee.setPhoneNumber("01700000000");
        savedEmployee.setDesignation("Software Engineer");
        savedEmployee.setJoiningDate(LocalDate.of(2026, 4, 1));
        savedEmployee.setStatus(StatusEnum.ACTIVE);
        savedEmployee.setDepartmentId(101L);
        savedEmployee.setSalary(Double.valueOf(50000));
        savedEmployee.setCreatedAt(LocalDate.now());
    }

    @Nested
    @DisplayName("saveEmployee() Tests")
    class SaveEmployeeTests {

        @Test
        @DisplayName("Should successfully save employee and send welcome email when email is unique")
        void shouldSaveEmployeeSuccessfully() {
            // Given
            when(employeeRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
            when(passwordEncoder.encode(anyString())).thenReturn("encoded_random_password");
            when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
            doNothing().when(emailService).sendSimpleEmail(any(EmailRequestDto.class));

            // When
            EmployeeAddResponseDTO response = employeeService.saveEmployee(requestDTO);

            // Then
            assertThat(response).isNotNull();
            assertThat(response.getId()).isEqualTo(1L);
            assertThat(response.getFullName()).isEqualTo("Fahad Nakib");
            assertThat(response.getEmail()).isEqualTo(requestDTO.getEmail());

            // Verify interactions & capture argument
            verify(employeeRepository, times(1)).existsByEmail(requestDTO.getEmail());
            verify(passwordEncoder, times(1)).encode(anyString());
            verify(employeeRepository, times(1)).save(employeeArgumentCaptor.capture());
            verify(emailService, times(1)).sendSimpleEmail(emailRequestCaptor.capture());

            // Validate captured entity state
            Employee capturedEmployee = employeeArgumentCaptor.getValue();
            assertThat(capturedEmployee.getFirstName()).isEqualTo("Fahad");
            assertThat(capturedEmployee.getPassword()).isEqualTo("encoded_random_password");

            // Validate captured email request
            EmailRequestDto capturedEmail = emailRequestCaptor.getValue();
            assertThat(capturedEmail.getToEmail()).isEqualTo("fahad@idealsoftware.com");
            assertThat(capturedEmail.getSubject()).isEqualTo("Welcome to IdealSoftware Limited");
        }

        @Test
        @DisplayName("Should throw EmailAlreadyExistsException when email already exists in DB")
        void shouldThrowExceptionWhenEmailExists() {
            // Given
            when(employeeRepository.existsByEmail(requestDTO.getEmail())).thenReturn(true);

            // When & Then
            assertThatThrownBy(() -> employeeService.saveEmployee(requestDTO))
                    .isInstanceOf(EmailAlreadyExistsException.class)
                    .hasMessage("Email address is already in use!");

            // Verify DB save and email send were never triggered
            verify(employeeRepository, times(1)).existsByEmail(requestDTO.getEmail());
            verify(passwordEncoder, never()).encode(anyString());
            verify(employeeRepository, never()).save(any(Employee.class));
            verify(emailService, never()).sendSimpleEmail(any(EmailRequestDto.class));
        }

        @Test
        @DisplayName("Should log error and return response DTO even if sending email throws MailException")
        void shouldHandleMailExceptionGracefully() {
            // Given
            when(employeeRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
            when(passwordEncoder.encode(anyString())).thenReturn("encoded_random_password");
            when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
            doThrow(new MailSendException("SMTP connection failed"))
                    .when(emailService).sendSimpleEmail(any(EmailRequestDto.class));

            // When
            EmployeeAddResponseDTO response = employeeService.saveEmployee(requestDTO);

            // Then
            assertThat(response).isNotNull();
            assertThat(response.getId()).isEqualTo(1L);

            // Verify save was still completed
            verify(employeeRepository, times(1)).save(any(Employee.class));
            verify(emailService, times(1)).sendSimpleEmail(any(EmailRequestDto.class));
        }
    }
}