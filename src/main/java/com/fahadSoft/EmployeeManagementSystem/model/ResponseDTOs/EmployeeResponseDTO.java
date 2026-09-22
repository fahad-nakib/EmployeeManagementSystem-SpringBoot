package com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs;

import com.fahadSoft.EmployeeManagementSystem.entity.enums.RoleEnum;
import com.fahadSoft.EmployeeManagementSystem.entity.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String designation;
    private RoleEnum role;
    private StatusEnum status;
}