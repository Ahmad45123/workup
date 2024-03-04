package com.workup.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workup.shared.commands.users.requests.FreelancerRegisterRequest;
import com.workup.shared.commands.users.responses.FreelancerRegisterResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RequestsAndResponsesTest {
    private static Object[] testObjects = { // all requests/responses to be tested
            FreelancerRegisterRequest.builder().build(),
            FreelancerRegisterResponse.builder().build(),
    };

    @Test
    public void testSerializationNoExceptionsThrown() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object testObject : testObjects) {
            String className = testObject.getClass().getName();
            assertDoesNotThrow(
                    () -> objectMapper.writeValueAsString(testObject),
                    String.format("Serialization of %s should not throw exceptions", className)
            );
            String json = objectMapper.writeValueAsString(testObject);
            assertDoesNotThrow(
                    () -> objectMapper.readValue(json, testObject.getClass()),
                    String.format("Deserialization of %s should not throw exceptions", className)
            );
        }
    }
}