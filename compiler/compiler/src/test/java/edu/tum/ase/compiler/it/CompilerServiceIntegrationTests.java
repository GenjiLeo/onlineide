package edu.tum.ase.compiler.it;

import edu.tum.ase.compiler.model.SourceCode;
import edu.tum.ase.compiler.service.CompilerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
public class CompilerServiceIntegrationTests {
    @Autowired
    private CompilerService systemUnderTest;

    // Call the compile function and check whether it returns the correct
    // status / warnings
    @Test
    public void should_CompileFileAndReturnResults_When_SourceCodeIsValid() {
        System.out.println("Integration test");
        // Given
        String fileName = "test.c";
        String fileContent = "int main() {printf(\"Hello World\"); return 0;}";
        SourceCode sourceCode = new SourceCode();
        ReflectionTestUtils.setField(sourceCode, "name", fileName);
        ReflectionTestUtils.setField(sourceCode, "content", fileContent);
        SourceCode expectedResult = new SourceCode();
        expectedResult.setCompilable(true);
        expectedResult.setStdout("");
        expectedResult.setStderr("test.c: In function ‘main’:\n" +
                "test.c:1:13: warning: implicit declaration of function ‘printf’ [-Wimplicit-function-declaration]\n" +
                " int main() {printf(\"Hello World\"); return 0;}\n" +
                "             ^~~~~~\n" +
                "test.c:1:13: warning: incompatible implicit declaration of built-in function ‘printf’\n" +
                "test.c:1:13: note: include ‘<stdio.h>’ or provide a declaration of ‘printf’\n" +
                "+#include <stdio.h>\n" +
                " int main() {printf(\"Hello World\"); return 0;}\n" +
                "             ^~~~~~\n");

        // When
        // This is an integration test since we call the system under test directly
        SourceCode result = systemUnderTest.compile(sourceCode);

        // Then
        then(result).isNotEqualTo(null);
        then(result.getCompilable()).isEqualTo(expectedResult.getCompilable());
        then(result.getStdout()).isEqualTo(expectedResult.getStdout());
        then(result.getStderr()).isEqualTo(expectedResult.getStderr());

    }

    @TestConfiguration
    static class CompilerServiceTestsConfiguration {
        @Bean
        public CompilerService systemUnderTest() {
            return new CompilerService();
        }
    }
}
