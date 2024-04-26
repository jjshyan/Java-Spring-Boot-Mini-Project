package com.springbootdemo.demo.service;

import com.springbootdemo.demo.model.Project;

import java.util.List;
public interface ProjectService {

    public Project createProject(Project project);
    public List<Project> getAllProjects();
    public Project getProjectById(Long projectId);
    public Project updateProject(Long projectId, Project project);
    public void deleteProject(Long projectId);
}
