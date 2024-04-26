package com.springbootdemo.demo.repo;

import com.springbootdemo.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentDepartmentId(Long departmentId);
}
