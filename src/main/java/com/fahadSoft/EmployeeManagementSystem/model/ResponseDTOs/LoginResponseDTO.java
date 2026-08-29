package com.fahadSoft.EmployeeManagementSystem.model.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String accessToken;

    @Builder.Default
    private String tokenType = "Bearer";

    // Client-side UI render করার সুবিধার্থে ইউজার ডাটা
    private Long id;
    private String email;
    private String firstName;
    private String designation;
}
