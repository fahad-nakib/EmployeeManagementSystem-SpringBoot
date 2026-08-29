package com.fahadSoft.EmployeeManagementSystem.config;

import com.fahadSoft.EmployeeManagementSystem.entity.Employee;
import com.fahadSoft.EmployeeManagementSystem.entity.enums.StatusEnum;
import com.fahadSoft.EmployeeManagementSystem.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@system.com";

        // ডাটাবেজে Admin আছে কি না চেক করা
        if (employeeRepository.findByEmail(adminEmail).isEmpty()) {
            Employee admin = new Employee();
            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setDesignation("ADMIN");
            admin.setStatus(StatusEnum.ACTIVE);
            admin.setCreatedAt(LocalDate.now());

            employeeRepository.save(admin);
            System.out.println(">>> Default Admin Account Created: admin@system.com / 123");
        }
    }
}