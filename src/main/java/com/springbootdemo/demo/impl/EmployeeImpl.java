package com.springbootdemo.demo.impl;

import com.springbootdemo.demo.model.Employee;
import com.springbootdemo.demo.model.Project;
import com.springbootdemo.demo.repo.EmployeeRepo;
import com.springbootdemo.demo.repo.ProjectRepo;
import com.springbootdemo.demo.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeImpl implements EmployeeService {

    @Resource
    EmployeeRepo employeeRepo;
    @Resource
    ProjectRepo projectRepo;

    @Override
    @CachePut(value = "employees", key = "#result.employeeId")
    public Employee createEmployee(Employee employee){
        return employeeRepo.save(employee);
    }

    @Override
    @Cacheable(value = "employees")
    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }

    @Override
    @Cacheable(value = "employees", key = "#employeeId")
    public Employee getEmployeeById(Long employeeId){
        return employeeRepo.findById(employeeId).orElse(null);
    }

    @Override
    @CachePut(value = "employees", key = "#employeeId")
    public Employee updateEmployee(Long employeeId, Employee employee){
        employee.setEmployeeId(employeeId);
        return employeeRepo.save(employee);
    }

    @Override
    @CacheEvict(value = "employees", key = "#employeeId")
    public void deleteEmployee(Long employeeId) {
        List<Project> projects = projectRepo.findByEmployeeEmployeeId(employeeId);
        for (Project project : projects) {
            project.setEmployee(null);
            projectRepo.save(project);
        }
        employeeRepo.deleteById(employeeId);
    }
}
