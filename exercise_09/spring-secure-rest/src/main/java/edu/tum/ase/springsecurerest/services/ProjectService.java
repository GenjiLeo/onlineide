package edu.tum.ase.springsecurerest.services;

import edu.tum.ase.springsecurerest.models.Project;
import edu.tum.ase.springsecurerest.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @PostFilter("filterObject.hasUserAccess(authentication.principal.username) || hasRole('ADMIN')")
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @PostAuthorize("returnObject.hasUserAccess(authentication.principal.username) || hasRole('ADMIN')")
    public Project findById(String projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Project createProject(Project project) {
        if (project.getId() != null) {
            return findById(project.getId());
        }
        project.addUser(userService.getUserFromSecurityContext());
        return projectRepository.save(project);
    }

}
