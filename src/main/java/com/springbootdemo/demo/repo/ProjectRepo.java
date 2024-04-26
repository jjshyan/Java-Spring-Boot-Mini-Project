package com.springbootdemo.demo.repo;

import com.springbootdemo.demo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    List<Project> findByEmployeeEmployeeId(Long employeeId);
}
