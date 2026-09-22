package com.fahadSoft.EmployeeManagementSystem.service.impl;

import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmailMessageDto;
import com.fahadSoft.EmployeeManagementSystem.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendSimpleEmail(EmailMessageDto emailMessageDto) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("fahadnakib.dev@gmail.com");
        mail.setTo(emailMessageDto.getToEmail());
        mail.setSubject(emailMessageDto.getSubject());
        mail.setText(emailMessageDto.getMessageBody());

        javaMailSender.send(mail);
        log.info("Email successfully dispatched via JavaMailSender to {}", emailMessageDto.getToEmail());
    }
}