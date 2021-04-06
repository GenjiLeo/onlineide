package edu.tum.ase.compiler.service;

import edu.tum.ase.compiler.model.SourceCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CompilerService {
    private static final Logger log = LoggerFactory.getLogger(CompilerService.class);

    // map file extensions to compilers
    private static final Map<String, String> compilers = new HashMap<String, String>()
    {
        {
            put(".java", "javac");
            put(".c", "gcc");
        }
    };

    public SourceCode compile(SourceCode sourceCode) {
        // Generate a folder name to use for this compilation task
        String folder = "/tmp/" + getUUID();
        File tempDirectory = setupCompilationFolder(folder);
        String fullFilename = folder + "/" + sourceCode.getName();
        setupFile(sourceCode.getContent(), fullFilename);
        String command = getCompiler(sourceCode.getName()) + " " + fullFilename;
        try {
            runCompiler(sourceCode, tempDirectory, command);
        } catch (IOException | InterruptedException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while trying to compile file");
        } finally {
            // Clean up the temporary directory after compilation
            if(!deleteDirectory(tempDirectory))
                log.warn("Could not delete directory " + folder);
        }
        return sourceCode;
    }

    public String getUUID() {
        return UUID.randomUUID().toString();
    }

    private void setupFile(String fileContent, String fullFilename) {
        File sourceFile = new File(fullFilename);
        try {
            if (sourceFile.createNewFile()) {
                // File should now exist
                FileWriter sourceCodeWriter = new FileWriter(fullFilename);
                sourceCodeWriter.write(fileContent);
                sourceCodeWriter.close();
            }
        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create file for compilation");
        }
    }

    private File setupCompilationFolder(String folder) {
        File tempDirectory = new File(folder);
        if (!tempDirectory.mkdir())
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while trying to set up compilation environment");
        return tempDirectory;
    }

    public static String getCompiler(String filename) {
        // Find out which compiler we'll need to use
        String fileExtension = getFileExtension(filename);
        System.out.println("Got file extension: " + fileExtension);
        if (!compilers.containsKey(fileExtension))
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "File is neither a .c nor a .java file but a " + fileExtension);
        return compilers.get(fileExtension);
    }

    private void runCompiler(SourceCode sourceCode, File tempDirectory, String command) throws IOException, InterruptedException {
        // Java / GCC will need to have been installed already for this to work
        // Run the compiler in the temporary directory to make sure that all output files are created there
        Process compilationProcess = Runtime.getRuntime().exec(command, null, tempDirectory);
        // We could set a timeout here so that the compilation process is canceled if it takes too long
        // But since security considerations were not our main concern it should not be an issue
        // Also we have three instances of the compiler service so hopefully another one would still be available
        int returnValue = compilationProcess.waitFor();
        sourceCode.setCompilable(returnValue == 0);
        String sanitizedStdout = sanitizeOutput(tempDirectory.getAbsolutePath(), readAll(compilationProcess.getInputStream()));
        sourceCode.setStdout(sanitizedStdout);
        String sanitizedStderr = sanitizeOutput(tempDirectory.getAbsolutePath(), readAll(compilationProcess.getErrorStream()));
        sourceCode.setStderr(sanitizedStderr);
    }

    private String sanitizeOutput(String tempDirectory, String output) {
        // Remove the temporary directory's name from the output to make it cleaner
        return output.replace(tempDirectory + "/", "");
    }

    // Read all lines from a stream
    private String readAll(java.io.InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String nextLine = null;
        // Read line by line and put newline characters in between
        while((nextLine = reader.readLine()) != null) {
            sb.append(nextLine);
            sb.append("\n");
        }
        return sb.toString();
    }

    private static String getFileExtension(String filename) {
        if(filename.contains("."))
            return filename.substring(filename.lastIndexOf("."));
        return null;
    }

    private boolean deleteDirectory(File dir) {
        String[] entries = dir.list();
        // We cannot delete a directory if it has any content so we need to
        // clear the directory first
        // This only supports a directory that only contains files (and not
        // other directories with files in them)
        // But that should be sufficient for our compiler output
        if (entries != null) {
            for(String s : entries){
                File currentFile = new File(dir.getPath(), s);
                currentFile.delete();
            }
        }
        return dir.delete();
    }
}