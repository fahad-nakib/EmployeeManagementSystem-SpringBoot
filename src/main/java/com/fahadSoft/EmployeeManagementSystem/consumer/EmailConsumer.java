package com.fahadSoft.EmployeeManagementSystem.consumer;

import com.fahadSoft.EmployeeManagementSystem.config.RabbitMQConfig;
import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmailMessageDto;
import com.fahadSoft.EmployeeManagementSystem.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void consumeEmailMessage(EmailMessageDto emailMessageDto) {
        log.info("Received email task from RabbitMQ queue for: {}", emailMessageDto.getToEmail());
        try {
            emailService.sendSimpleEmail(emailMessageDto);
            log.info("Successfully processed email for: {}", emailMessageDto.getToEmail());
        } catch (Exception e) {
            log.error("Error sending email via Consumer to {}: {}", emailMessageDto.getToEmail(), e.getMessage(), e);
            throw e; // Rethrowing allows RabbitMQ retry mechanism / DLQ to trigger
        }
    }
}