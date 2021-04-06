package edu.tum.ase.project.service;

import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.model.SourceFile;
import edu.tum.ase.project.repository.ProjectRepository;
import edu.tum.ase.project.repository.SourceFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class SourceFileService {
    private static final Logger log = LoggerFactory.getLogger(SourceFileService.class);
    @Autowired
    private SourceFileRepository sourceFileRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRepository projectRepository;


    public SourceFile createSourceFile(SourceFile request) {
        if(request.getName() == null || request.getName().equals("")) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Illegal file name");
        }
        log.info("Creating file: " + request.getName());
        if (request.getProject() == null)
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Source files cannot be created without being assigned to a project");
        log.info("Project ID: " + request.getProject().getId());
        Project project = projectService.findById(request.getProject().getId());
        // Check whether the user is allowed to write to this project
        ProjectService.checkAuthorization(project);
        log.info("Project name: " + project.getName());
        SourceFile sourceFile = new SourceFile(project, request.getName());
        sourceFile.setContent(request.getContent());
        log.info("Source file created: " + sourceFile.getName());
        project.addSourceFile(sourceFile);
        projectRepository.flush();
        return sourceFileRepository.save(sourceFile);
    }

    @PostAuthorize("returnObject.getProject().hasAccess(authentication.name)")
    public SourceFile findById(String id) {
        Optional<SourceFile> sourceFile = sourceFileRepository.findById(id);
        if (sourceFile.isEmpty())
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        return sourceFile.get();
    }

    public SourceFile updateSourceFile(SourceFile request) {
        SourceFile sourceFile = findById(request.getId());
        ProjectService.checkAuthorization(sourceFile.getProject());
        // Set new name if a new name is given
        if(request.getName() != null) {
            if(request.getName().equals(""))
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Illegal file name");
            sourceFile.setName(request.getName());
        }
        // Set new content if new content is given
        if (request.getContent() != null)
            sourceFile.setContent(request.getContent());
        sourceFileRepository.flush();
        return sourceFile;
    }

    public void deleteSourceFile(SourceFile request) {
        SourceFile sourceFile = findById(request.getId());
        ProjectService.checkAuthorization(sourceFile.getProject());
        sourceFileRepository.delete(sourceFile);
    }
}