package com.springbootdemo.demo.service;

import com.springbootdemo.demo.model.Department;

import java.util.List;

public interface DepartmentService {

    public Department createDepartment(Department department);
    public List<Department> getAllDepartments();
    public Department getDepartmentById(Long departmentId);
    public Department updateDepartment(Long departmentId, Department department);
    public void deleteDepartment(Long departmentId);
}
