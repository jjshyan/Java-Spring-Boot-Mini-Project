package com.springbootdemo.demo.impl;

import com.springbootdemo.demo.model.Department;
import com.springbootdemo.demo.model.Employee;
import com.springbootdemo.demo.repo.DepartmentRepo;
import com.springbootdemo.demo.repo.EmployeeRepo;
import com.springbootdemo.demo.service.DepartmentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentImpl implements DepartmentService {

    @Resource
    DepartmentRepo departmentRepo;
    @Resource
    EmployeeRepo employeeRepo;

    @Override
    public Department createDepartment(Department department){
        return departmentRepo.save(department);
    }

    @Override
    public List<Department> getAllDepartments(){
        return departmentRepo.findAll();
    }

    @Override
    public Department getDepartmentById(Long departmentId){
        return departmentRepo.findById(departmentId).orElse(null);
    }

    @Override
    public Department updateDepartment(Long departmentId, Department department){
        department.setDepartmentId(departmentId);
        return departmentRepo.save(department);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        List<Employee> employees = employeeRepo.findByDepartmentDepartmentId(departmentId);
        for (Employee employee : employees) {
            employee.setDepartment(null);
            employeeRepo.save(employee);
        }
        departmentRepo.deleteById(departmentId);
    }
}
