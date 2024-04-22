package com.workup.contracts;

import com.workup.contracts.commands.ContractCommandMap;
import com.workup.contracts.commands.HandleTerminationRequestCommand;
import com.workup.contracts.commands.InitiateContractCommand;
import com.workup.contracts.commands.PrintContractCommand;
import com.workup.contracts.commands.RequestContractTerminationCommand;
import com.workup.shared.commands.contracts.requests.ContractTerminationRequest;
import com.workup.shared.commands.contracts.requests.HandleTerminationRequest;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.requests.PrintContractRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.commands.contracts.responses.HandleTerminationResponse;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.commands.contracts.responses.PrintContractResponse;
import com.workup.shared.commands.jobs.responses.CreateJobResponse;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "contractsqueue")
public class RabbitMQListener {

  @Autowired
  public ContractCommandMap commandMap;

  @RabbitHandler
  public InitiateContractResponse receive(InitiateContractRequest in) throws Exception {
    InitiateContractResponse response =
      ((InitiateContractCommand) commandMap.getCommand("InitiateContract")).Run(in);
    return response;
  }

  @RabbitHandler
  public ContractTerminationResponse receive(ContractTerminationRequest in)
    throws Exception {
    return (
      (RequestContractTerminationCommand) commandMap.getCommand(
        "RequestContractTermination"
      )
    ).Run(in);
  }

  @RabbitHandler
  public HandleTerminationResponse receive(HandleTerminationRequest in) throws Exception {
    return (
      (HandleTerminationRequestCommand) commandMap.getCommand("HandleTerminationRequest")
    ).Run(in);
  }

  @RabbitHandler
  public PrintContractResponse receive(PrintContractRequest in) throws Exception {
    PrintContractResponse response =
      ((PrintContractCommand) commandMap.getCommand("PrintContract")).Run(in);
    return response;
  }
  // NEW_COMMAND_BOILERPLATE

}
