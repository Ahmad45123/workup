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
  @RabbitHandler
  public InitiateContractResponse receive(InitiateContractRequest in) throws Exception {
    System.out.println("Received: " + in);
    return InitiateContractResponse.builder()
        .withContractId("123")
        .withStatusCode(HttpStatusCode.CREATED)
        .build();
  }
}
