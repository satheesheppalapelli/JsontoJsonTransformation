package com.example.jsontransformation.TransformController;

import com.example.jsontransformation.Entity.TransformedData;
import com.example.jsontransformation.Entity.TransformedDataRepository;
import com.example.jsontransformation.JoltService.JoltService;
import com.example.jsontransformation.JoltService.TransformedDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TransformController {

    private final JoltService joltService;
    private final TransformedDataService transformedDataService;
    private final TransformedDataRepository transformedDataRepository;

    public TransformController(JoltService joltService, TransformedDataService transformedDataService, TransformedDataRepository transformedDataRepository) {
        this.joltService = joltService;
        this.transformedDataService = transformedDataService;
        this.transformedDataRepository = transformedDataRepository;
    }

    @PostMapping("/transform")
    public ResponseEntity<String> transformJson(@RequestBody String jsonInput) {
        try {
            if (joltService == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("JoltService is not properly initialized.");
            }
            String transformedJson = joltService.transform(jsonInput);
            if (transformedJson == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Transformation resulted in null.");
            }
            transformedDataService.saveTransformedData(transformedJson);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully Stored the Transformed Event into Database: " + transformedJson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/transformed_json")
    public Iterable<TransformedData> getAllTransformedData() {
        return transformedDataRepository.findAll();
    }

    @GetMapping("/transformed_json/{id}")
    public ResponseEntity<TransformedData> getTransformedDataById(@PathVariable Long id) {
        Optional<TransformedData> optionalTransformedData = transformedDataRepository.findById(id);
        return optionalTransformedData.map(data -> ResponseEntity.ok().body(data))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}