package com.workup.contracts;

import com.workup.contracts.commands.*;
import com.workup.shared.commands.contracts.requests.ContractTerminationRequest;
import com.workup.shared.commands.contracts.requests.HandleTerminationRequest;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.requests.ViewContractMilestonesRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.commands.contracts.responses.HandleTerminationResponse;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.commands.contracts.responses.ViewContractMilestonesResponse;
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
  public ViewContractMilestonesResponse receive(ViewContractMilestonesRequest in) throws Exception {
    return (
            (ViewContractMilestonesCommand) commandMap.getCommand("ViewMilestone")
    ).Run(in);
  }
  // NEW_COMMAND_BOILERPLATE

}
