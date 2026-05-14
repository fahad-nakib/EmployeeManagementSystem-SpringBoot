package com.fahadSoft.EmployeeManagementSystem.repository;

import com.fahadSoft.EmployeeManagementSystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(String department);
}
