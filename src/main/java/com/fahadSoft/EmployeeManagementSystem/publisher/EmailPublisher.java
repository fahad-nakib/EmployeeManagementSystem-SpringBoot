package com.fahadSoft.EmployeeManagementSystem.publisher;

import com.fahadSoft.EmployeeManagementSystem.config.RabbitMQConfig;
import com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs.EmailMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendEmailEvent(EmailMessageDto emailMessageDto) {
        log.info("Publishing email event to RabbitMQ queue for recipient: {}", emailMessageDto.getToEmail());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EMAIL_EXCHANGE,
                RabbitMQConfig.EMAIL_ROUTING_KEY,
                emailMessageDto
        );
    }
}