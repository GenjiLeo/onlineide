package edu.tum.ase.monolith.controllers;

import edu.tum.ase.monolith.models.Project;
import edu.tum.ase.monolith.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@Controller
public class ProjectController {

    @Autowired
    private RestTemplate restTemplate;

    private final String BACKEND_BASE_URL = "http://localhost:8081/project";
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("projects", restTemplate.getForObject(BACKEND_BASE_URL,Project[].class,));
        return "index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("project", new Project());
        return "create";
    }

    // Note: We are using HTTP GET here for simplicity.
    // Generally, you should use the correct HTTP method for a delete action (HTTP DELETE).
    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable("id") String projectId, Model model) {
        restTemplate.delete(BACKEND_BASE_URL);
        return "redirect:/";
    }

    @PostMapping(value = "/create")
    public String createProject(@Valid Project project, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create";
        }
        restTemplate.postForObject(BACKEND_BASE_URL,project, Project.class);
        return "redirect:/";
    }
}
