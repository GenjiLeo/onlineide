package edu.tum.ase.backendservice;

import edu.tum.ase.backendservice.models.Project;
import edu.tum.ase.backendservice.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BackendServiceApplication implements CommandLineRunner {

    @Autowired
    private ProjectRepository projectRepository;

    public static void main(String[] args) {
        SpringApplication.run(BackendServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Project project1 = new Project();
        project1.setName("project-1");
        Project project2 = new Project();
        project2.setName("project-2");
        projectRepository.save(project1);
        projectRepository.save(project2);
    }

}
