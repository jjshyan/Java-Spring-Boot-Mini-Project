package com.springbootdemo.demo.service;

import com.springbootdemo.demo.model.Employee;

import java.util.List;

public interface EmployeeService {

    public Employee createEmployee(Employee employee);
    public List<Employee> getAllEmployees();
    public Employee getEmployeeById(Long employeeId);
    public Employee updateEmployee(Long employeeId, Employee employee);
    public void deleteEmployee(Long employeeId);
}
