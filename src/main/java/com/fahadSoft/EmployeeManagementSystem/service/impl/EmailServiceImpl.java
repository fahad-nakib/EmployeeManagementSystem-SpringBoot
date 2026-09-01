package com.fahadSoft.EmployeeManagementSystem.service.impl;

import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmailRequestDto;
import com.fahadSoft.EmployeeManagementSystem.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendSimpleEmail(EmailRequestDto emailRequestDto){
        try {
//            SimpleMailMessage mail = new SimpleMailMessage();
//            mail.setFrom("fahadnakib.dev@gmail.com");
//            mail.setTo(emailRequestDto.getToEmail());
//            mail.setSubject(emailRequestDto.getSubject());
//            mail.setText(emailRequestDto.getMessageBody());

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom("fahadnakib.dev@gmail.com", "Md. Fahad Nakib");
            helper.setTo(emailRequestDto.getToEmail());
            helper.setSubject(emailRequestDto.getSubject());
            helper.setText(emailRequestDto.getMessageBody(), false);

            javaMailSender.send(mimeMessage);

        }catch (Exception e){
            log.error("Exception while send Email", e);
        }
    }
}
