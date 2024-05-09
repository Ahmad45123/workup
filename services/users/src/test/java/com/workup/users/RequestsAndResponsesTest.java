package com.workup.users;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workup.shared.commands.users.requests.AuthenticationRequest;
import com.workup.shared.commands.users.requests.ClientRegisterRequest;
import com.workup.shared.commands.users.requests.FreelancerRegisterRequest;
import com.workup.shared.commands.users.requests.LoginRequest;
import com.workup.shared.commands.users.responses.SignUpAndInResponse;
import org.junit.jupiter.api.Test;

public class RequestsAndResponsesTest {
  private static Object[] testObjects = { // all requests/responses to be tested
    FreelancerRegisterRequest.builder().build(),
    SignUpAndInResponse.builder().build(),
    AuthenticationRequest.builder().build(),
    ClientRegisterRequest.builder().build(),
    LoginRequest.builder().build()
  };

  @Test
  public void testSerializationNoExceptionsThrown() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    for (Object testObject : testObjects) {
      String className = testObject.getClass().getName();
      assertDoesNotThrow(
          () -> objectMapper.writeValueAsString(testObject),
          String.format("Serialization of %s should not throw exceptions", className));
      String json = objectMapper.writeValueAsString(testObject);
      assertDoesNotThrow(
          () -> objectMapper.readValue(json, testObject.getClass()),
          String.format("Deserialization of %s should not throw exceptions", className));
    }
  }
}
