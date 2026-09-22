package com.fahadSoft.EmployeeManagementSystem.service;

import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmailMessageDto;

public interface EmailService {
    void sendSimpleEmail(EmailMessageDto emailMessageDto);
}
