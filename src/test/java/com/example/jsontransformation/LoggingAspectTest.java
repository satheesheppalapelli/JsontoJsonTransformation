package com.example.jsontransformation;

import com.example.jsontransformation.Aspects.LoggingAspect;
import nl.altindag.log.LogCaptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoggingAspectTest {

    private LoggingAspect loggingAspect;
    private LogCaptor logCaptor;

    @BeforeEach
    void setUp() {
        loggingAspect = new LoggingAspect();
        logCaptor = LogCaptor.forClass(LoggingAspect.class);
    }

    @Test
    void testSimpleLogging() {
        Logger testLogger = LoggerFactory.getLogger(LoggingAspect.class);
        testLogger.info("This is a test log message");

        logCaptor.getInfoLogs().forEach(System.out::println);
        assertTrue(logCaptor.getInfoLogs().contains("This is a test log message"), "Test log message was not captured");
    }

    @Test
    void testLogAroundAspect() throws Throwable {
        // Given
        String inputJson = "{\"foo\":\"value\"}";
        String transformedJson = "{\"bar\":\"value\"}";

        // Mocking the ProceedingJoinPoint
        ProceedingJoinPoint joinPoint = mockProceedingJoinPoint(inputJson, transformedJson);

        // When
        Object result = loggingAspect.logMethodExecution(joinPoint);

        // Assert the method's result
        assertEquals(transformedJson, result);

        // Print logs to verify
        logCaptor.getInfoLogs().forEach(System.out::println);

        // Then: Verify logs are captured correctly
        assertTrue(logCaptor.getInfoLogs().stream()
                        .anyMatch(log -> log.contains("Logging before proceeding on Method: transformJson, with Arguments: [{\"foo\":\"value\"}]")),
                "Expected 'before' log message not found.");

        assertTrue(logCaptor.getInfoLogs().stream()
                        .anyMatch(log -> log.contains("Logging after proceeding on Method: transformJson, that Returned value: {\"bar\":\"value\"}")),
                "Expected 'after' log message not found.");
    }

    @Test
    void testLogExecutionTime() throws Throwable {
        // Given
        String inputJson = "{\"foo\":\"value\"}";
        String transformedJson = "{\"bar\":\"value\"}";

        // Mocking the ProceedingJoinPoint
        ProceedingJoinPoint joinPoint = mockProceedingJoinPoint(inputJson, transformedJson);

        // When
        Object result = loggingAspect.logMethodExecution(joinPoint);

        // Assert the method's result
        assertEquals(transformedJson, result);

        // Print logs to verify
        logCaptor.getInfoLogs().forEach(System.out::println); // Debugging line

        // Then: Verify that the execution time log is captured
        assertTrue(logCaptor.getInfoLogs().stream()
                        .anyMatch(log -> log.contains("Execution Time: transformJson:: executed in")),
                "Expected execution time log message not found.");
    }

    private ProceedingJoinPoint mockProceedingJoinPoint(String input, String output) throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        Signature signature = mock(Signature.class);

        // Mock the behavior of getSignature() and getName()
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("transformJson");

        // Mock the arguments and the result of proceed()
        when(joinPoint.getArgs()).thenReturn(new Object[]{input});
        when(joinPoint.proceed()).thenReturn(output);

        return joinPoint;
    }

}