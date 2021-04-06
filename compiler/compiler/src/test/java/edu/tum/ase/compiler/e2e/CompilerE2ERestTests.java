package edu.tum.ase.compiler.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tum.ase.compiler.model.SourceCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompilerE2ERestTests {
    private final String URL = "/compile";

    @Autowired
    private MockMvc systemUnderTest;

    @Autowired
    private ObjectMapper objectMapper;

    // Tests if given C code returns the expected warning
    @Test
    public void should_ReturnCompilationResultsAsREST_When_SourceCodeIsCompilable() throws Exception {
        System.out.println("end-to-end test");
        // Given
        String fileName = "test.c";
        String fileContent = "int main() {printf(\"Hello World\"); return 0;}";
        SourceCode sourceCode = new SourceCode();
        ReflectionTestUtils.setField(sourceCode, "name", fileName);
        ReflectionTestUtils.setField(sourceCode, "content", fileContent);
        SourceCode expectedResult = new SourceCode();
        expectedResult.setCompilable(true);
        expectedResult.setStdout("");
        // Expected output for GCC 8
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
        // Starts the Spring application and performs a POST request (end2end test)
        ResultActions result = systemUnderTest.perform(post(URL).content(objectMapper.writeValueAsString(sourceCode)).contentType(MediaType.APPLICATION_JSON));

        // Then
        result
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.compilable").value(expectedResult.getCompilable()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stdout").value(expectedResult.getStdout()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stderr").value(expectedResult.getStderr()));
    }
}
