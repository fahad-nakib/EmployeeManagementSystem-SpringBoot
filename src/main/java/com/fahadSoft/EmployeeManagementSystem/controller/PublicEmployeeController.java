package com.fahadSoft.EmployeeManagementSystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees/check")
@RequiredArgsConstructor
public class PublicEmployeeController {

    @GetMapping
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("Server is live", HttpStatus.OK);
    }
}
