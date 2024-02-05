package com.example.jsontransformation;

import com.example.jsontransformation.JoltService.JoltService;
import com.example.jsontransformation.TransformController.TransformController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CustomJsonTransformationApplicationTests {

	@InjectMocks
	private TransformController transformController;

	@Mock
	private JoltService joltService;

	@BeforeEach
	public void setUp() {
		// Initialize the TransformController with the mocked JoltService
		transformController = new TransformController(joltService);
	}

	@Test
	public void testTransformJsonSuccess() throws Exception {
		// Given
		String inputJson = "{\"foo\":\"value\"}";
		String expectedJson = "{\"bar\":\"value\"}";
		when(joltService.transform(inputJson)).thenReturn(expectedJson);

		// When
		ResponseEntity<String> response = transformController.transformJson(inputJson);

		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedJson, response.getBody());
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
