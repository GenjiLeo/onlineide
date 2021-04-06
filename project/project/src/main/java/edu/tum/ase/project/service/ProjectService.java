package edu.tum.ase.project.service;

import edu.tum.ase.project.model.GitlabUser;
import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.model.SourceFile;
import edu.tum.ase.project.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.security.oauth2.client.OAuth2RestOperations;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);
    @Autowired
    private ProjectRepository projectRepository;

    private static final String lrzGitlabUserApi = "https://gitlab.lrz.de/api/v4/users?username=";

    @Autowired
    private OAuth2RestOperations restTemplate;

    public Project createProject(Project request) {
        if (request.getName() == null || request.getName().equals(""))
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Illegal project name");
        // Enforce unique project names
        if (!isNameAvailable(request.getName()))
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Name is already taken");
        // Add the user who created the project as a contributor so that they have access to the project
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Project project = new Project(request.getName(), username);

        log.info("Project created: " + project.getName() + " for user " + username);
        return projectRepository.save(project);
    }

    @PostAuthorize("returnObject.hasAccess(authentication.name)")
    public Project findByName(String name) {
        return projectRepository.findByName(name);
    }

    @PostAuthorize("returnObject.hasAccess(authentication.name)")
    public Project findById(String id) {
        Optional<Project> op = projectRepository.findById(id);
        if (op.isEmpty())
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        return op.get();
    }

    @PostFilter("filterObject.hasAccess(authentication.name)")
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public Project updateProject(Project request) {
        if (request.getName() == null || request.getName().equals(""))
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Illegal project name");
        log.info("Updating project name to " + request.getName());
        if (!isNameAvailable(request.getName()))
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Name is already taken");
        Project project = findById(request.getId());
        checkAuthorization(project);
        System.out.println("Updating project " + project.getId());
        project.setName(request.getName());
        // If there are any other properties in the future we can update them here as well
        projectRepository.flush();
        return project;
    }

    public void deleteProject(Project request) {
        log.info("Deleting project " + request.getId());
        Project project = findById(request.getId());
        checkAuthorization(project);
        projectRepository.delete(project);
    }

    public List<SourceFile> getSourceFiles(Project request) {
        Project project = findById(request.getId());
        checkAuthorization(project);
        return project.getSourceFiles();
    }

    // Check with the Gitlab API whether the user exists and store their Gitlab
    // username in the list of contributors
    // We do not support the case of someone changing their Gitlab username
    public String shareProject(String id, String username) {
        Project project = findById(id);
        checkAuthorization(project);
        System.out.println("Adding user " + username + " to project " + id);
        // Get all GitLab users with the given username
        GitlabUser[] resultArray = restTemplate.getForObject(lrzGitlabUserApi + username, GitlabUser[].class);
        // Makes sure there is exactly one user returned; if that is not the case return null
        GitlabUser result = resultArray != null && resultArray.length == 1 ? resultArray[0] : null;
        if (result != null && result.getUsername() != null && result.getState().equals("active") && !project.hasAccess(username)) {
            // Add user to list of users the project is shared with
            project.addContributor(username);
            projectRepository.flush();
            System.out.println("User successfully granted access to project, Gitlab user ID: " + result.getId());
            return "{\"name\":\"" + username + "\"}";
        }
        System.out.println("Could not find gitlab user with name or user had already access to the project: " + username);
        // Return bad request if the user was not found / not unique etc. so that the frontend can display an error message
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    private boolean isNameAvailable(String name) {
        return findByName(name) == null;
    }

    // We cannot do this check with @PreAuthorize because we do not have the object there
    // and we cannot do it with @PostAuthorize because then the damage is already done
    // Just checks whether the username is part of the contributor list
    public static void checkAuthorization(Project project) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!project.hasAccess(username)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

}
