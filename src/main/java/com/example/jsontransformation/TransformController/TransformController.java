package com.example.jsontransformation.TransformController;

import com.example.jsontransformation.Entity.TransformedData;
import com.example.jsontransformation.Entity.TransformedDataRepository;
import com.example.jsontransformation.JoltService.JoltService;
import com.example.jsontransformation.JoltService.TransformedDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TransformController {

    @Autowired
    private JoltService joltService;

    @Autowired
    private TransformedDataService transformedDataService;

    @Autowired
    private TransformedDataRepository transformedDataRepository;

    public TransformController(JoltService joltService, TransformedDataService transformedDataService) {
        this.joltService = joltService;
        this.transformedDataService = transformedDataService;
    }

    @PostMapping("/transform")
    public ResponseEntity<String> transformJson(@RequestBody String jsonInput) {
        try {
            if (joltService == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("JoltService is not properly initialized.");
            }
            String transformedJson = joltService.transform(jsonInput);
            transformedDataService.saveTransformedData(transformedJson);
//            return ResponseEntity.ok().build();
            return ResponseEntity.status(HttpStatus.OK).body("Successfully Stored the Transformed Event into Database: " + transformedJson);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping()
    @Cacheable(value = "transformed_json")
    public Iterable<TransformedData> getAllTransformedData() {
        return transformedDataRepository.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "transformed_json")
    public ResponseEntity<TransformedData> getTransformedDataById(@PathVariable Long id) {
        Optional<TransformedData> optionalTransformedData = transformedDataRepository.findById(id);
        if (!optionalTransformedData.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalTransformedData.get());
    }
}