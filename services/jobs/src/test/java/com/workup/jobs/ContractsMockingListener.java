package com.workup.jobs;

import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import java.util.concurrent.CompletableFuture;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.CONTRACTS)
public class ContractsMockingListener {

  public static String contractIdToBeReturned;
  public static HttpStatusCode statusCodeToBeReturned;

  @RabbitHandler
  @Async
  public CompletableFuture<InitiateContractResponse> receive(InitiateContractRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          InitiateContractResponse.builder()
              .withContractId(contractIdToBeReturned)
              .withStatusCode(statusCodeToBeReturned)
              .build());
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          InitiateContractResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }
}
