package com.example.jsontransformation.TransformController;

import com.example.jsontransformation.JoltService.JoltService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransformController {

    private final JoltService joltService;

    public TransformController(JoltService joltService) {
        this.joltService = joltService;
    }

    @PostMapping("/transform")
    public ResponseEntity<String> transformJson(@RequestBody String jsonInput) {
        try {
            String transformedJson = joltService.transform(jsonInput);
            return ResponseEntity.ok(transformedJson);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during transformation");
        }
    }
}
