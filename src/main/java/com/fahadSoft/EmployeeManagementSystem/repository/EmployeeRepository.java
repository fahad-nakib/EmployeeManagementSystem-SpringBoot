package com.fahadSoft.EmployeeManagementSystem.repository;

import com.fahadSoft.EmployeeManagementSystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
