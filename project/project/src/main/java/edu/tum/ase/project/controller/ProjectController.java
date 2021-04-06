package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.model.SourceFile;
import edu.tum.ase.project.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    ProjectService projectService;

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<Object> exception(HttpClientErrorException e) {
        log.warn("Caught exception: " + e.getStatusText() + ", Response code: " + e.getStatusCode());
        // You could probably wrap the error text in JSON in a better way
        return new ResponseEntity<>("{\n\"error\": \"" + e.getStatusText() + "\"\n}", e.getStatusCode());
    }

    // Create bean for Gitlab API requests
    @Bean
    public OAuth2RestOperations restTemplate(OAuth2ClientContext context) {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        return new OAuth2RestTemplate(details, context);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/project")
    public Project createProject(@RequestBody Project request) {
        System.out.println("Creating project");
        // Create a new project to ensure that only the name from the request is used
        return projectService.createProject(request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/project/findByName/{name}")
    public Project findByName(@PathVariable("name") String name) {
        return projectService.findByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/project/findAll")
    public List<Project> findAll() {
        return projectService.getProjects();
    }

    // Find by project ID
    @RequestMapping(method = RequestMethod.GET, value = "/project/findById/{id}")
    public Project findById(@PathVariable("id") String id) {
        return projectService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/project")
    public Project changeProject(@RequestBody Project request) {
        return projectService.updateProject(request);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/project")
    public void deleteProject(@RequestBody Project request) {
        projectService.deleteProject(request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/project/share/{id}/{username}")
    public String shareProject(@PathVariable("id") String id, @PathVariable("username") String username) {
        return projectService.shareProject(id, username);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/project/files/{id}")
    public List<SourceFile> getSourceFiles(@PathVariable("id") String id) {
        return projectService.getSourceFiles(projectService.findById(id));
    }

}
