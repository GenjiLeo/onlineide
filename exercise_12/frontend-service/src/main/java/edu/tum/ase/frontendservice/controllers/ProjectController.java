package edu.tum.ase.frontendservice.controllers;

import edu.tum.ase.frontendservice.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ProjectController {
// Talk  to gateway -> registry  // Talk to registry  // Talk to BE directly
    private final String BACKEND_BASE_URL =  "/project/" //"http://backend-service/project/";//"http://localhost:8081/project/";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/")
    public String index(Model model) {

        //obtain all ip addresses of deployed services
        //could provide custom load balancing implementation
        List<InstanceInfo> discoveryClient. = discoveryClient.getInstancesById("backend-service");
        serviceInstaceList.forEach((InstanceInfo instanceInfo) -> {
            System.out.println("Info "+  instanceInfo.getIPAddr());
        })


        model.addAttribute("projects", restTemplate.getForObject(BACKEND_BASE_URL, Project[].class));
        return "index";
    }
}
