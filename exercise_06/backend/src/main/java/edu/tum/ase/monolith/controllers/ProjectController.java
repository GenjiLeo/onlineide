package edu.tum.ase.monolith.controllers;

import edu.tum.ase.monolith.models.Project;
import edu.tum.ase.monolith.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public List<Project> getProjects(){
        return projectService.getProjects();
    }
    @PostMapping("/")
    public  Project createProject(@RequestBody Project project){
         return projectService.createProject(project)
    }
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") String projectId){
        Project project = projectService.findById(projectId);
        projectService.deleteProject(project);
    }




}
