package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.SourceFile;
import edu.tum.ase.project.service.SourceFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class SourceFileController {

    private static final Logger log = LoggerFactory.getLogger(SourceFileController.class);
    @Autowired
    SourceFileService sourceFileService;

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<Object> exception(HttpClientErrorException e) {
        log.warn("Caught exception: " + e.getStatusText() + ", Response code: " + e.getStatusCode());
        // You could probably wrap the error text in JSON in a better way
        return new ResponseEntity<>("{\n\"error\": \"" + e.getStatusText() + "\"\n}", e.getStatusCode());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/project/sourceFile")
    public SourceFile createSourceFile(@RequestBody SourceFile request) {
        // Create a new sourceFile to ensure that only the name from the request is used
        return sourceFileService.createSourceFile(request);
    }

    // Find by sourceFile ID
    @RequestMapping(method = RequestMethod.GET, value = "/project/sourceFile/findById")
    public SourceFile findById(@RequestBody SourceFile request) {
        return sourceFileService.findById(request.getId());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/project/sourceFile")
    public SourceFile changeSourceFile(@RequestBody SourceFile request) {
        return sourceFileService.updateSourceFile(request);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/project/sourceFile")
    public void deleteSourceFile(@RequestBody SourceFile request) {
        sourceFileService.deleteSourceFile(request);
    }
}
