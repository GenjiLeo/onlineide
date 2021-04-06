package edu.tum.ase.springtestpyramid.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tum.ase.springtestpyramid.models.Project;
import edu.tum.ase.springtestpyramid.repositories.ProjectRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Create a real spring boot application
// Limit it, do not start a real webserver
// Serializing and such as disabled
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectE2ERestTests {

    private final String URL = "/project/";

    // Perform request handled as if a real web server is there
    @Autowired
    private MockMvc systemUnderTest;

    @Autowired
    private ProjectRepository projectRepository;
    // Performs the serialization of requests
    @Autowired
    private ObjectMapper objectMapper;

    @After
    public void tearDown() {
        projectRepository.deleteAll();
    }

    @Test
    public void should_ReturnPersistedProject_When_PostWithProject() throws Exception {
        // given
        Project project = new Project();
        project.setName("Test-project");
        Project createdProject = projectRepository.save(project);

        // when
        ResultActions result = systemUnderTest.perform(post(URL)
                .content(objectMapper.writeValueAsString(project))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(createdProject.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(createdProject.getName()));
    }
    @Before
    public void setUp(){
        projectRepository.deleteAll();
    }

    @Test
    public void should_ReturnEmptyProject_When_RequestedWithInvalidId(){
        // given
        String projectId = "123";
        // when
        ResultActions result = systemUnderTest.perform(get(URL+projectId));
        // then
        result
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isEmpty());
    }

}
