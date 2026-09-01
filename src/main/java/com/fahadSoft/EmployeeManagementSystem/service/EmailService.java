package com.fahadSoft.EmployeeManagementSystem.service;

import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmailRequestDto;

public interface EmailService {
    void sendSimpleEmail(EmailRequestDto emailRequestDto);
}
