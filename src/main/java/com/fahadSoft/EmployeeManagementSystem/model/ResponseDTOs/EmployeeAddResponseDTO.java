package com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs;

import com.fahadSoft.EmployeeManagementSystem.entity.enums.StatusEnum;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeAddResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String designation;
    private LocalDate joiningDate;
    private StatusEnum status;
    private Long departmentId;
    private Double salary;
}
