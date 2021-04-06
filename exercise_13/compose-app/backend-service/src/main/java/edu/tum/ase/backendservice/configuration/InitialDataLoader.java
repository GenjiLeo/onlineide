package edu.tum.ase.backendservice.configuration;

import edu.tum.ase.backendservice.models.Project;
import edu.tum.ase.backendservice.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements CommandLineRunner {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void run(String... args) {
        projectRepository.deleteAll();
        Project project1 = new Project();
        project1.setName("project-1");
        Project project2 = new Project();
        project2.setName("project-2");
        projectRepository.save(project1);
        projectRepository.save(project2);
    }

}
