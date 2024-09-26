package com.example.jsontransformation;

import com.example.jsontransformation.Entity.TransformedDataRepository;
import com.example.jsontransformation.JoltService.JoltService;
import com.example.jsontransformation.JoltService.TransformedDataService;
import com.example.jsontransformation.TransformController.TransformController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest()
class TestTransformedDataRepository {

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

    @Test
    void testTransformJsonSuccess() {

        // Given
        String inputJson = "{\"foo\":\"value\"}";
        String transformedJson = "{\"bar\":\"value\"}";
        when(joltService.transform(inputJson)).thenReturn(transformedJson);

        // When
        ResponseEntity<String> response = transformController.transformJson(inputJson);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(transformedDataService).saveTransformedData(transformedJson);
    }
}