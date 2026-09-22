package com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessageDto implements Serializable {
    private String toEmail;
    private String subject;
    private String messageBody;
}