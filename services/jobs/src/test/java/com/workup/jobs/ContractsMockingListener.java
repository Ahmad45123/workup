package com.workup.jobs;

import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.enums.HttpStatusCode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "contractsqueue")
public class ContractsMockingListener {

  public static String contractIdToBeReturned;
  public static HttpStatusCode statusCodeToBeReturned;

  @RabbitHandler
  public InitiateContractResponse receive(InitiateContractRequest in) throws Exception {
    return InitiateContractResponse.builder()
        .withContractId(contractIdToBeReturned)
        .withStatusCode(statusCodeToBeReturned)
        .build();
  }
}
