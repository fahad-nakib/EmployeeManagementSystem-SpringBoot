package com.fahadSoft.EmployeeManagementSystem.controller;

import com.fahadSoft.EmployeeManagementSystem.entity.Employee;
import com.fahadSoft.EmployeeManagementSystem.model.EmployeeAddRequest;
import com.fahadSoft.EmployeeManagementSystem.model.EmployeeAddResponse;
import com.fahadSoft.EmployeeManagementSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public EmployeeAddResponse addEmployee(@RequestBody EmployeeAddRequest data)
    {
        return service.addEmployee(data);
    }

    @GetMapping("/allFullDetail")
    public List<Employee> getAllEmployeeFullDetail(){
        return service.getAllEmployeeFullDetail();
    }

    @GetMapping("/allBasic")
    public List<EmployeeAddResponse> getAllEmployeeBasicInfo(){
        return service.getAllEmployeeBasicInfo();
    }

    @GetMapping("/id/{id}")
    public Employee getEmployeeById(@PathVariable Long id){
        return service.getEmployeeById(id);
    }

    @GetMapping("/dept/{dept}")
    public List<Employee> getEmployeeByDepartment(@PathVariable("dept") String department){   //if mapping variable and function variable not same then declare it inside the @Pathvariable notation
        return service.getEmployeeByDepartment(department);
    }

    @PutMapping("/update/{id}")
    public Employee updateEmployee(@RequestBody EmployeeAddRequest request, @PathVariable Long id){
        return service.updateEmployee(request,id);
    }

    @DeleteMapping("delete/{id}")
    public Map<String,String> deleteEmployee(@PathVariable Long id){
        return service.deleteEmployee(id);
    }

    @DeleteMapping("delete/all")
    public Map<String,String> deleteAllEmployee(){
        return service.deleteAllEmployee();
    }


}
