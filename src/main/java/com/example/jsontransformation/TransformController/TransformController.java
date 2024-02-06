package com.example.jsontransformation.TransformController;

import com.example.jsontransformation.Entity.TransformedData;
import com.example.jsontransformation.Entity.TransformedDataRepository;
import com.example.jsontransformation.JoltService.JoltService;
import com.example.jsontransformation.JoltService.TransformedDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransformController {

    @Autowired
    private JoltService joltService;

    @Autowired
    private TransformedDataService transformedDataService;

    public TransformController(JoltService joltService) {
        this.joltService = joltService;
    }

    @PostMapping("/transform")
    public ResponseEntity<String> transformJson(@RequestBody String jsonInput) {
        try {
            if (joltService == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("JoltService is not properly initialized.");
            }
            String transformedJson = joltService.transform(jsonInput);
            transformedDataService.saveTransformedData(transformedJson);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during transformation");
        }
    }
}