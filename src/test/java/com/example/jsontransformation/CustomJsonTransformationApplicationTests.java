package com.example.jsontransformation;
import com.example.jsontransformation.Entity.TransformedData;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
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

	@Test
	public void testTransformJsonSuccess() throws Exception {
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

	@Test
	public void testTransformJsonFailure() throws Exception {
		// Given
		String inputJson = "invalid json";
		when(joltService.transform(inputJson)).thenThrow(new RuntimeException("Error during transformation"));

		// When
		ResponseEntity<String> response = transformController.transformJson(inputJson);

		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
