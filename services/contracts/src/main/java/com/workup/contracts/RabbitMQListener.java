package com.workup.contracts;

import com.workup.contracts.commands.*;
import com.workup.shared.commands.contracts.requests.ContractTerminationRequest;
import com.workup.shared.commands.contracts.requests.EvaluateMilestoneRequest;
import com.workup.shared.commands.contracts.requests.GetContractRequest;
import com.workup.shared.commands.contracts.requests.GetPendingTerminationsRequest;
import com.workup.shared.commands.contracts.requests.HandleTerminationRequest;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.requests.MarkPaymentCompletedRequest;
import com.workup.shared.commands.contracts.requests.ProgressMilestoneRequest;
import com.workup.shared.commands.contracts.requests.ViewContractMilestonesRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.commands.contracts.responses.EvaluateMilestoneResponse;
import com.workup.shared.commands.contracts.responses.GetContractResponse;
import com.workup.shared.commands.contracts.responses.GetPendingTerminationsResponse;
import com.workup.shared.commands.contracts.responses.HandleTerminationResponse;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.commands.contracts.responses.MarkPaymentCompletedResponse;
import com.workup.shared.commands.contracts.responses.ProgressMilestoneResponse;
import com.workup.shared.commands.contracts.responses.ViewContractMilestonesResponse;
import com.workup.shared.enums.ServiceQueueNames;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.CONTRACTS)
public class RabbitMQListener {

  @Autowired public ContractCommandMap commandMap;

  @RabbitHandler
  public InitiateContractResponse receive(InitiateContractRequest in) throws Exception {
    InitiateContractResponse response =
        ((InitiateContractCommand) commandMap.getCommand("InitiateContract")).Run(in);
    return response;
  }

  @RabbitHandler
  public ContractTerminationResponse receive(ContractTerminationRequest in) throws Exception {
    return ((RequestContractTerminationCommand) commandMap.getCommand("RequestContractTermination"))
        .Run(in);
  }

  @RabbitHandler
  public HandleTerminationResponse receive(HandleTerminationRequest in) throws Exception {
    return ((HandleTerminationRequestCommand) commandMap.getCommand("HandleTerminationRequest"))
        .Run(in);
  }

  @RabbitHandler
  public MarkPaymentCompletedResponse receive(MarkPaymentCompletedRequest in) throws Exception {
    return ((MarkMilestoneAsPaidCommand) commandMap.getCommand("MarkMilestoneAsPaid")).Run(in);
  }

  @RabbitHandler
  public ViewContractMilestonesResponse receive(ViewContractMilestonesRequest in) throws Exception {
    return ((ViewContractMilestonesCommand) commandMap.getCommand("ViewContractMilestones"))
        .Run(in);
  }

  @RabbitHandler
  public GetContractResponse receive(GetContractRequest in) throws Exception {
    System.out.println("** ENTERED GET CONTRACT RABBITMQ");
    return ((GetContractCommand) commandMap.getCommand("GetContract")).Run(in);
  }

  @RabbitHandler
  public EvaluateMilestoneResponse receive(EvaluateMilestoneRequest in) throws Exception {
    return ((EvaluateMilestoneCommand) commandMap.getCommand("EvaluateMilestone")).Run(in);
  }

  @RabbitHandler
  public ProgressMilestoneResponse receive(ProgressMilestoneRequest in) throws Exception {
    return ((ProgressMilestoneCommand) commandMap.getCommand("ProgressMilestone")).Run(in);
  }

  @RabbitHandler
  public GetPendingTerminationsResponse receive(GetPendingTerminationsRequest in) throws Exception {
    return ((GetPendingTerminationsCommand) commandMap.getCommand("GetPendingTerminations"))
        .Run(in);
  }
  // NEW_COMMAND_BOILERPLATE

}
