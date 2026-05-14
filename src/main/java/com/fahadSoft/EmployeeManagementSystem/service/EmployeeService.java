package com.fahadSoft.EmployeeManagementSystem.service;

import com.fahadSoft.EmployeeManagementSystem.entity.Employee;
import com.fahadSoft.EmployeeManagementSystem.model.EmployeeAddRequest;
import com.fahadSoft.EmployeeManagementSystem.model.EmployeeAddResponse;
import com.fahadSoft.EmployeeManagementSystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;


    public EmployeeAddResponse addEmployee(EmployeeAddRequest request)
    {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());

        Employee storedEmployee = repository.save(employee);
        return new EmployeeAddResponse(storedEmployee.getId(), storedEmployee.getName());
    }

    public List<Employee> getAllEmployeeFullDetail(){
        return repository.findAll();
    }

    public List<EmployeeAddResponse> getAllEmployeeBasicInfo(){

        List<Employee> dbData = repository.findAll();
        List<EmployeeAddResponse> employees = new ArrayList<>();

        for (Employee e : dbData){
            EmployeeAddResponse emp = new EmployeeAddResponse();
            emp.setId(e.getId());
            emp.setName(e.getName());

            employees.add(emp);
        }
        return employees;
    }

    public Employee getEmployeeById(Long id){
        if (id == null) return null;
//        Optional<Employee> dbEmployee = repository.findById(id);
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found !! "));
    }

    public List<Employee> getEmployeeByDepartment(String department){
        if(department == null) return null;
        return repository.findByDepartment(department);
    }

    public Employee updateEmployee(EmployeeAddRequest request, Long id){
        if (id == null || request == null) return null;
        Employee dbEmployee = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found !!"));
        if (request.getName() != null) dbEmployee.setName(request.getName());
        if (request.getDepartment() != null) dbEmployee.setDepartment(request.getDepartment());
        if (request.getSalary() != null) dbEmployee.setSalary(request.getSalary());

        return repository.save(dbEmployee);
    }

    public Map<String,String> deleteEmployee(Long id){
        if (id == null) return Map.of("status", "Id not provided to delete.");
        repository.deleteById(id);
        return Map.of("status", "Delete successful of ID : "+id);
    }

    public Map<String,String> deleteAllEmployee(){
        repository.deleteAll();
        return Map.of("status", "All Employee has been deleted");
    }
}
