package edu.tum.ase.springangularoauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class SpringAngularOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringAngularOauth2Application.class, args);
    }

    @GetMapping("/api/project")
    public List<Project> getProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1, "Learning C"));
        projects.add(new Project(2, "ASE Project"));
        projects.add(new Project(3, "My favorite language Java"));
        return projects;
    }

    private static class Project {
        public int id;
        public String name;

        Project(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
