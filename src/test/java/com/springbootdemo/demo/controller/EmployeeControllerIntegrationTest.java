package com.springbootdemo.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.springbootdemo.demo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeName("lim");
        employee.setPosition("Junior Developer");

        // Create basic authentication
        String username = "admin";
        String password = "admin";
        String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

        // Create request headers and set authentication credentials
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeaderValue);

        HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/employees", request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllEmployees() {
        String username = "admin";
        String password = "admin";
        String authHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeaderValue);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Employee>> response = restTemplate.exchange(
                "/api/employees",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Employee>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetEmployeeById() {
        long id = 1L;
        ResponseEntity<Employee> response = restTemplate.getForEntity("/api/employees/" + id, Employee.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateEmployee() {

        long id = 1L;

        Employee updatedEmployee = new Employee();
        updatedEmployee.setEmployeeName("lim");
        updatedEmployee.setPosition("Junior Developer");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Employee> requestEntity = new HttpEntity<>(updatedEmployee, headers);
        ResponseEntity<String> response = restTemplate.exchange("/api/employees/" + id, HttpMethod.PUT, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteEmployee() {

        long id = 1L;
        ResponseEntity<String> response = restTemplate.exchange("/api/employees/" + id, HttpMethod.DELETE, null, String.class);

        if (!response.getBody().equals("Employee with id - " + id + " does not exist.")) {
            assertEquals(HttpStatus.OK, response.getStatusCode());
        } else {
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }
}