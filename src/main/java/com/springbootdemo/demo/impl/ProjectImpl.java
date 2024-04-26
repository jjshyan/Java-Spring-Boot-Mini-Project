package com.springbootdemo.demo.impl;

import com.springbootdemo.demo.model.Project;
import com.springbootdemo.demo.repo.ProjectRepo;
import com.springbootdemo.demo.service.ProjectService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectImpl implements ProjectService {

    @Resource
    ProjectRepo projectRepo;

    @Override
    public Project createProject(Project project){
        return projectRepo.save(project);
    }

    @Override
    public List<Project> getAllProjects(){
        return projectRepo.findAll();
    }

    @Override
    public Project getProjectById(Long projectId){
        return projectRepo.findById(projectId).orElse(null);
    }

    @Override
    public Project updateProject(Long projectId, Project project){
        project.setProjectId(projectId);
        return projectRepo.save(project);
    }

    @Override
    public void deleteProject(Long projectId){
        projectRepo.deleteById(projectId);
    }
}
