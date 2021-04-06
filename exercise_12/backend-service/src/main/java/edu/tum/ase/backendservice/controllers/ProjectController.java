package edu.tum.ase.backendservice.controllers;

import edu.tum.ase.backendservice.models.Project;
import edu.tum.ase.backendservice.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/")
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }
}
