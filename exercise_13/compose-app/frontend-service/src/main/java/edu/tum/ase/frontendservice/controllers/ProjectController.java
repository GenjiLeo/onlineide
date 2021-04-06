package edu.tum.ase.frontendservice.controllers;

import edu.tum.ase.frontendservice.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ProjectController {

    @Value("${backend.url}")
    private String backendUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("projects", restTemplate.getForObject(backendUrl + "/project/", Project[].class));
        return "index";
    }
}
