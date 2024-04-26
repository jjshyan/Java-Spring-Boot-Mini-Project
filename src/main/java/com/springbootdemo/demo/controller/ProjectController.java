package com.springbootdemo.demo.controller;

import com.springbootdemo.demo.model.Project;
import com.springbootdemo.demo.impl.ProjectImpl;
import com.springbootdemo.demo.repo.ProjectRepo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectImpl projectImpl;
    @Resource
    private ProjectRepo projectRepo;

    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation error: " + result.getAllErrors().get(0).getDefaultMessage());
        }
        projectImpl.createProject(project);
        return ResponseEntity.ok("Successfully created.");
    }

    @GetMapping
    public List<Project> getAllProjects(){
        return projectImpl.getAllProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id){
        return projectImpl.getProjectById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @Valid @RequestBody Project project, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation error: " + result.getAllErrors().get(0).getDefaultMessage());
        }
        projectImpl.updateProject(id, project);
        return ResponseEntity.ok("Successfully updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        if (!projectRepo.existsById(id)) {
            return ResponseEntity.badRequest().body("Project with id - " + id + " does not exist.");
        }
        projectImpl.deleteProject(id);
        return ResponseEntity.ok("Successfully deleted.");
    }
}
