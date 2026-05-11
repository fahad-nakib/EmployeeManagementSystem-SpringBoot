package com.fahadSoft.EmployeeManagementSystem.controller;

import com.fahadSoft.EmployeeManagementSystem.model.EmployeeAddRequest;
import com.fahadSoft.EmployeeManagementSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/check")
    public String check (){
        return "Server is running...";
    }

    @PostMapping("/add")
    public String addEmployee(@RequestBody EmployeeAddRequest data)
    {
        service.addEmployee(data);
        return "Employee Added Successfully.";
    }
}
