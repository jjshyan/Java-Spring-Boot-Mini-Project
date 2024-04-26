package com.springbootdemo.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.springbootdemo.demo.impl.EmployeeImpl;
import com.springbootdemo.demo.model.Employee;
import com.springbootdemo.demo.repo.EmployeeRepo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeControllerTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private EmployeeImpl employeeImpl;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void createEmployee() {
        Employee employee = new Employee();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        ResponseEntity<?> response = employeeController.createEmployee(employee, bindingResult);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllEmployees() {
        List<Employee> expectedEmployees = Collections.singletonList(new Employee());
        when(employeeImpl.getAllEmployees()).thenReturn(expectedEmployees);
        List<Employee> actualEmployees = employeeController.getAllEmployees();
        assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    void getEmployeeById() {
        Long id = 1L;
        Employee expectedEmployee = new Employee();
        when(employeeImpl.getEmployeeById(id)).thenReturn(expectedEmployee);
        Employee actualEmployee = employeeController.getEmployeeById(id);
        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    void updateEmployee() {
        Long id = 1L;
        Employee employee = new Employee();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        ResponseEntity<?> response = employeeController.updateEmployee(id, employee, bindingResult);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteEmployee() {
        Long id = 1L;
        when(employeeRepo.existsById(id)).thenReturn(true);
        ResponseEntity<?> response = employeeController.deleteEmployee(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}