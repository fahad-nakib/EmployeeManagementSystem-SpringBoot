package com.fahadSoft.EmployeeManagementSystem.model;

public class EmployeeAddResponse {
    private Long id;
    private String name;

    public EmployeeAddResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeeAddResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
