package edu.tum.ase.springsecurerest.controllers;

import edu.tum.ase.springsecurerest.models.Project;
import edu.tum.ase.springsecurerest.repositories.ProjectRepository;
import edu.tum.ase.springsecurerest.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public List<Project> listProjects() {
        return projectService.findAll();
    }

    @GetMapping("/{projectId}")
    public Project getProject(@PathVariable String projectId) {
        return projectService.findById(projectId);
    }

    @PostMapping(value = "/")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }
}
