package com.springbootdemo.demo.repo;

import com.springbootdemo.demo.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
}
