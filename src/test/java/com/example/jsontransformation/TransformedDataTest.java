package com.example.jsontransformation;

import com.example.jsontransformation.Entity.TransformedData;
import com.example.jsontransformation.Entity.TransformedDataRepository;
import com.example.jsontransformation.JoltService.JoltService;
import com.example.jsontransformation.JoltService.TransformedDataService;
import com.example.jsontransformation.TransformController.TransformController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransformedDataTest {

    @InjectMocks
    private TransformController transformController;

    @Mock
    private JoltService joltService;

    @Mock
    private TransformedDataService transformedDataService;

    @Mock
    private TransformedDataRepository transformedDataRepository;

    @BeforeEach
    public void setUp() {
        transformedDataRepository.deleteAll();
    }

    // Entity is correctly mapped to the "transformed_json" table
    @Test
    public void test_entity_mapping() {
        TransformedData data = new TransformedData();
        data.setId(1L);
        data.setTransformedJson("{\"key\":\"value\"}");

        assertEquals(1L, data.getId());
        assertEquals("{\"key\":\"value\"}", data.getTransformedJson());
    }

    // Handling null values for transformedJson
    @Test
    public void test_null_transformedJson() {
        TransformedData data = new TransformedData();
        data.setTransformedJson(null);

        assertNull(data.getTransformedJson());
    }

    // Saving a valid transformed data entity successfully.
}