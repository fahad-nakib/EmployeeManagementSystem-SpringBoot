package com.fahadSoft.EmployeeManagementSystem.service;


import com.fahadSoft.EmployeeManagementSystem.entity.Employee;
import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.LoginRequestDTO;
import com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs.LoginResponseDTO;
import com.fahadSoft.EmployeeManagementSystem.security.AuthService;
import com.fahadSoft.EmployeeManagementSystem.security.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Unit Tests")
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthUtil authUtil;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    private LoginRequestDTO loginRequestDTO;
    private Employee employee;

    @BeforeEach
    void setUp() {
        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("fahad@idealsoftware.com");
        loginRequestDTO.setPassword("Password@123");

        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Fahad");
        employee.setEmail("fahad@idealsoftware.com");
        employee.setDesignation("Backend Developer");
    }

    @Nested
    @DisplayName("login() Tests")
    class LoginTests {

        @Test
        @DisplayName("Should successfully authenticate user and return accessToken with user details")
        void shouldLoginSuccessfully() {
            // Given
            String mockToken = "mocked.jwt.access.token";
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(employee);
            when(authUtil.generateAccessToken(employee)).thenReturn(mockToken);

            // When
            LoginResponseDTO response = authService.login(loginRequestDTO);

            // Then
            assertThat(response).isNotNull();
            assertThat(response.getAccessToken()).isEqualTo(mockToken);
            assertThat(response.getId()).isEqualTo(1L);
            assertThat(response.getEmail()).isEqualTo("fahad@idealsoftware.com");
            assertThat(response.getFirstName()).isEqualTo("Fahad");
            assertThat(response.getDesignation()).isEqualTo("Backend Developer");

            verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(authUtil, times(1)).generateAccessToken(employee);
        }

        @Test
        @DisplayName("Should throw BadCredentialsException when invalid credentials are provided")
        void shouldThrowExceptionWhenCredentialsAreInvalid() {
            // Given
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenThrow(new BadCredentialsException("Invalid credentials"));

            // When & Then
            assertThatThrownBy(() -> authService.login(loginRequestDTO))
                    .isInstanceOf(BadCredentialsException.class)
                    .hasMessage("Invalid credentials");

            verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(authUtil, never()).generateAccessToken(any());
        }
    }
}