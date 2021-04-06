package edu.tum.ase.compiler.controller;

import edu.tum.ase.compiler.model.SourceCode;
import edu.tum.ase.compiler.service.CompilerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin
public class CompilerController {
    @Autowired
    private CompilerService compilerService;

    // Internal logger, prints to stdout
    private static final Logger log = LoggerFactory.getLogger(CompilerController.class);

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<Object> exception(HttpClientErrorException e) {
        log.warn("Caught exception: " + e.getStatusText() + ", Response code: " + e.getStatusCode());
        // You could probably wrap the error text in JSON in a better way
        return new ResponseEntity<>("{\n\"error\": \"" + e.getStatusText() + "\"\n}", e.getStatusCode());
    }

    @RequestMapping(path = "/compile", method = RequestMethod.POST)
    public SourceCode compile(@RequestBody SourceCode sourceCode) {
        return compilerService.compile(sourceCode);
    }
}