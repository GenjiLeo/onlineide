package edu.tum.ase.springtestpyramid.unit;

import edu.tum.ase.springtestpyramid.controllers.ProjectController;
import edu.tum.ase.springtestpyramid.models.Project;
import edu.tum.ase.springtestpyramid.repositories.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class ProjectControllerTests {

    @Autowired
    private ProjectController systemUnderTest;

    @MockBean
    private ProjectRepository projectRepository;

    @Test
    public void should_ReturnExistingProject_When_ProjectNameExisting() {
        // given
        Project existingProject = new Project();
        existingProject.setId("1");
        existingProject.setName("Test-project");
        Project project = new Project();
        project.setName("Test-project");
        // Provides a stub of the project repository
        given(projectRepository.findByName(anyString())).willReturn(Optional.of(existingProject));

        // when
        Project result = systemUnderTest.createProject(project);

        // then
        then(result.getId()).isEqualTo(existingProject.getId());
        then(result.getName()).isEqualTo(existingProject.getName());
    }

    @Test
    public void should_ReturnEmptyProject_When_IdNotFound(){
        //given
        String projectid = "123";
        given(projectRepository.findById(anyString())).willReturn(Optional.empty());
        // when
        Project actualResult = systemUnderTest.getProject(projectid);
        // then
        then(actualResult.getId()).isEqualTo(null);
        then(actualResult.getName()).isEqualTo(null);
    }

    @TestConfiguration
    static class ProjectControllerTestsConfiguration {

        @Bean
        public ProjectController systemUnderTest() {
            return new ProjectController();
        }
    }
}
