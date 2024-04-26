package com.springbootdemo.demo.controller;

import com.springbootdemo.demo.model.Employee;
import com.springbootdemo.demo.impl.EmployeeImpl;
import com.springbootdemo.demo.repo.EmployeeRepo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeImpl employeeImpl;
    @Resource
    private EmployeeRepo employeeRepo;

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        employeeImpl.createEmployee(employee);
        return ResponseEntity.ok("Successfully created.");
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeImpl.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id){
        return employeeImpl.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        employeeImpl.updateEmployee(id, employee);
        return ResponseEntity.ok("Successfully updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        if (!employeeRepo.existsById(id)) {
            return ResponseEntity.badRequest().body("Employee with id - " + id + " does not exist.");
        }
        employeeImpl.deleteEmployee(id);
        return ResponseEntity.ok("Successfully deleted.");
    }

}
