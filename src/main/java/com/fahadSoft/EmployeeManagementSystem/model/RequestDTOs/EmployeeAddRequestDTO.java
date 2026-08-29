package com.fahadSoft.EmployeeManagementSystem.model.RequestDTOs;

import com.fahadSoft.EmployeeManagementSystem.entity.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeAddRequestDTO {

    @NotBlank(message = "First name must not be empty or null")
    @Size(max = 30, message = "First name length must not exceed 30 characters")
    @Pattern(
            regexp = "^[a-zA-Z ]+$",
            message = "First name must contain only alphabets and spaces"
    )
    private String firstName;

    @Pattern(
            regexp = "^[a-zA-Z ]*$",
            message = "Last name must contain only alphabets and spaces"
    )
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Please provide a valid email address"
    )
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least 1 letter, 1 number, and 1 special character"
    )
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9+ ]{10,15}$",
            message = "Please provide a valid phone number"
    )
    private String phoneNumber;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;

    @NotNull(message = "Status is required")
    private StatusEnum status;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Salary is required")
    private Double salary;
}
