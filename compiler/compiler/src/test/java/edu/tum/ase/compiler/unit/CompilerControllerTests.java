package edu.tum.ase.compiler.unit;

import edu.tum.ase.compiler.controller.CompilerController;
import edu.tum.ase.compiler.model.SourceCode;
import edu.tum.ase.compiler.service.CompilerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
public class CompilerControllerTests {

    @Autowired
    private CompilerController systemUnderTest;

    @MockBean
    private CompilerService compilerService;

    // Test if given the compile function in the CompilerController actually
    // returns what it is given by the CompilerService
    @Test
    public void should_ReturnSourceCodeWithCompilationResults_When_CompilationIsSuccessful() {
        System.out.println("Unit test");

        // Given
        String fileName = "test.c";
        String fileContent = "int main() {return 0;}";
        SourceCode sourceCode = new SourceCode();
        ReflectionTestUtils.setField(sourceCode, "name", fileName);
        ReflectionTestUtils.setField(sourceCode, "content", fileContent);
        SourceCode expectedResult = new SourceCode();
        expectedResult.setCompilable(true);
        expectedResult.setStdout("Successfully compiled");
        expectedResult.setStderr("No errors");
        given(compilerService.compile(sourceCode)).willReturn(expectedResult);

        // When
        SourceCode result = systemUnderTest.compile(sourceCode);

        // Then
        then(result.getCompilable()).isEqualTo(expectedResult.getCompilable());
        then(result.getStdout()).isEqualTo(expectedResult.getStdout());
        then(result.getStderr()).isEqualTo(expectedResult.getStderr());
    }

    // Test whether the function that chooses the compiler properly finds GCC
    // for a .c file
    @Test
    public void should_returnGccCompiler_When_FilenameEndsWithC() {
        System.out.println("Second Unit test");

        // Given
        String filename = "file.c";
        String expectedResult = "gcc";

        // When
        String result = CompilerService.getCompiler(filename);

        // Then
        then(result).isNotNull();
        then(result).isEqualTo(expectedResult);
    }

    @TestConfiguration
    static class CompilerControllerTestsConfiguration {
        @Bean
        public CompilerController systemUnderTest() {
            return new CompilerController();
        }
    }
}
