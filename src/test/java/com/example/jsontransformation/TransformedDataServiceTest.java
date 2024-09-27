package com.example.jsontransformation;

import com.example.jsontransformation.Entity.TransformedData;
import com.example.jsontransformation.Entity.TransformedDataRepository;
import com.example.jsontransformation.JoltService.JoltService;
import com.example.jsontransformation.JoltService.TransformedDataService;
import com.example.jsontransformation.TransformController.TransformController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransformedDataServiceTest {

    @InjectMocks
    private TransformController transformController;

    @Mock
    private JoltService joltService;

    @Mock
    private TransformedDataService transformedDataService;

    @Mock
    private TransformedDataRepository transformedDataRepository;

    private TransformedDataService MakeTransformedDataServiceWithMockRepository(TransformedDataRepository mockRepository) {
        return new TransformedDataService(mockRepository);
    }
    // save valid transformed JSON data
    @Test
    public void test_save_valid_transformed_json_data() {
        TransformedDataRepository mockRepository = Mockito.mock(TransformedDataRepository.class);
        TransformedDataService service = new TransformedDataService(mockRepository);

        String validJson = "{\"key\":\"value\"}";
        TransformedData expectedData = new TransformedData();
        expectedData.setTransformedJson(validJson);

        Mockito.when(mockRepository.save(Mockito.any(TransformedData.class))).thenReturn(expectedData);

        TransformedData result = service.saveTransformedData(validJson);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(validJson, result.getTransformedJson());
        Mockito.verify(mockRepository, Mockito.times(1)).save(Mockito.any(TransformedData.class));
    }
    // save empty string as transformed JSON data
    @Test
    public void test_save_empty_string_as_transformed_json_data() {
        TransformedDataRepository mockRepository = Mockito.mock(TransformedDataRepository.class);
        TransformedDataService service = new TransformedDataService(mockRepository);

        String emptyJson = "";
        TransformedData expectedData = new TransformedData();
        expectedData.setTransformedJson(emptyJson);

        Mockito.when(mockRepository.save(Mockito.any(TransformedData.class))).thenReturn(expectedData);

        TransformedData result = service.saveTransformedData(emptyJson);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(emptyJson, result.getTransformedJson());
        Mockito.verify(mockRepository, Mockito.times(1)).save(Mockito.any(TransformedData.class));
    }

    @Test
    public void SaveTransformedData_CallsRepositorySave() {
        TransformedDataRepository mockRepository = mock(TransformedDataRepository.class);
        TransformedDataService service = MakeTransformedDataServiceWithMockRepository(mockRepository);
        Object transformedJson = "{\"key\":\"value\"}";
        TransformedData expectedData = new TransformedData();
        expectedData.setTransformedJson(transformedJson.toString());
        when(mockRepository.save(Mockito.any(TransformedData.class))).thenReturn(expectedData);

        // Act
        service.saveTransformedData(transformedJson);

        // Assert
        ArgumentCaptor<TransformedData> captor = ArgumentCaptor.forClass(TransformedData.class);
        verify(mockRepository).save(captor.capture());
        assertEquals(transformedJson.toString(), captor.getValue().getTransformedJson());
    }
}