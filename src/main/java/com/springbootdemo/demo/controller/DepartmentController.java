package com.springbootdemo.demo.controller;

import com.springbootdemo.demo.model.Department;
import com.springbootdemo.demo.impl.DepartmentImpl;
import com.springbootdemo.demo.repo.DepartmentRepo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentImpl departmentImpl;
    @Resource
    DepartmentRepo departmentRepo;

    @PostMapping
    public ResponseEntity<?> createDepartment(@Valid @RequestBody Department department, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation error: " + result.getAllErrors().get(0).getDefaultMessage());
        }
        departmentImpl.createDepartment(department);
        return ResponseEntity.ok("Successfully created.");
    }

    @GetMapping
    public List<Department> getAllDepartments(){
        return departmentImpl.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id){
        return departmentImpl.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @Valid @RequestBody Department department, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation error: " + result.getAllErrors().get(0).getDefaultMessage());
        }
        departmentImpl.updateDepartment(id, department);
        return ResponseEntity.ok("Successfully updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        if (!departmentRepo.existsById(id)) {
            return ResponseEntity.badRequest().body("Department with id - " + id + " does not exist.");
        }
        departmentImpl.deleteDepartment(id);
        return ResponseEntity.ok("Successfully deleted.");
    }
}
