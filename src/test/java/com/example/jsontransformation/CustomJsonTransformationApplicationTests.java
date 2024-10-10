package com.example.jsontransformation;

import com.example.jsontransformation.Repository.TransformedDataRepository;
import com.example.jsontransformation.Service.JoltService;
import com.example.jsontransformation.Service.TransformedDataService;
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

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransformControllerTest {

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
        MockitoAnnotations.openMocks(this);
        this.transformedDataRepository.deleteAll();
    }

    // Transform JSON input and store the result in the database
    @Test
    void test_transform_json_and_store_in_db() {
        // Arrange
        JoltService joltService = mock(JoltService.class);
        TransformedDataService transformedDataService = mock(TransformedDataService.class);
        TransformedDataRepository transformedDataRepository = mock(TransformedDataRepository.class);
        TransformController transformController = new TransformController(joltService, transformedDataService, transformedDataRepository);
        String jsonInput = "{\"key\":\"value\"}";
        String transformedJson = "{\"newKey\":\"newValue\"}";

        when(joltService.transform(jsonInput)).thenReturn(transformedJson);

        // Act
        ResponseEntity<String> response = transformController.transformJson(jsonInput);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Successfully Stored the Transformed Event into Database"));
        verify(transformedDataService, times(1)).saveTransformedData(transformedJson);
    }

    @Test
    void testTransformJson_NullJoltService() {
        TransformController controllerWithNullJoltService = new TransformController(null, transformedDataService, transformedDataRepository);

        ResponseEntity<String> response = controllerWithNullJoltService.transformJson("{\"key\":\"value\"}");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("JoltService is not properly initialized.", response.getBody());
    }

    @Test
    void test_transform_json_null_input() {
        String jsonInput = null;

        ResponseEntity<String> response = transformController.transformJson(jsonInput);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Transformation resulted in null."));
    }
}