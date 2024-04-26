package com.springbootdemo.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;
    @NotBlank(message = "Employee name can't be left empty.")
    private String employeeName;
    @NotBlank(message = "Position can't be left empty.")
    private String position;
    @ManyToOne
    @JoinColumn(name = "departmentId", referencedColumnName = "departmentId")
    private Department department;

    public Employee(){};

    public Employee(long employeeId, String employeeName, String position, Department department) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.position = position;
        this.department = department;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
