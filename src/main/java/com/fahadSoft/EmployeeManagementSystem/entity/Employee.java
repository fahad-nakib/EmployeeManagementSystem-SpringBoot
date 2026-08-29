package com.fahadSoft.EmployeeManagementSystem.entity;

import com.fahadSoft.EmployeeManagementSystem.entity.enums.RoleEnum;
import com.fahadSoft.EmployeeManagementSystem.entity.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(length = 30)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String designation;

    private RoleEnum role;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    private StatusEnum status;

    @Column(name = "department_id")
    private Long departmentId;

    private Double salary;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == null) {
            return List.of();
        }
        // "ROLE_" প্রিফিক্স সহ GrantedAuthority রিটার্ন করতে হবে
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
