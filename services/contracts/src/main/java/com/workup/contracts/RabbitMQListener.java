package com.workup.contracts;

import com.workup.contracts.commands.*;
import com.workup.shared.commands.contracts.requests.*;
import com.workup.shared.commands.contracts.responses.*;
import com.workup.shared.enums.ServiceQueueNames;
import java.util.concurrent.CompletableFuture;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.CONTRACTS)
public class RabbitMQListener {

  @Autowired public ContractCommandMap commandMap;

  @RabbitHandler
  @Async
  public CompletableFuture<InitiateContractResponse> receive(InitiateContractRequest in)
      throws Exception {
    InitiateContractResponse response =
        ((InitiateContractCommand) commandMap.getCommand("InitiateContract")).Run(in);
    return CompletableFuture.completedFuture(response);
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ContractTerminationResponse> receive(ContractTerminationRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        ((RequestContractTerminationCommand) commandMap.getCommand("RequestContractTermination"))
            .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<HandleTerminationResponse> receive(HandleTerminationRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        ((HandleTerminationRequestCommand) commandMap.getCommand("HandleTerminationRequest"))
            .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<MarkPaymentCompletedResponse> receive(MarkPaymentCompletedRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        ((MarkMilestoneAsPaidCommand) commandMap.getCommand("MarkMilestoneAsPaid")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ViewContractMilestonesResponse> receive(ViewContractMilestonesRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        ((ViewContractMilestonesCommand) commandMap.getCommand("ViewContractMilestones")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetContractResponse> receive(GetContractRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        ((GetContractCommand) commandMap.getCommand("GetContract")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<EvaluateMilestoneResponse> receive(EvaluateMilestoneRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        ((EvaluateMilestoneCommand) commandMap.getCommand("EvaluateMilestone")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ProgressMilestoneResponse> receive(ProgressMilestoneRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        ((ProgressMilestoneCommand) commandMap.getCommand("ProgressMilestone")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetPendingTerminationsResponse> receive(GetPendingTerminationsRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        ((GetPendingTerminationsCommand) commandMap.getCommand("GetPendingTerminations")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetMilestoneResponse> receive(GetMilestoneRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        ((GetMilestoneCommand) commandMap.getCommand("GetMilestone")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<PrintContractResponse> receive(PrintContractRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        ((PrintContractCommand) commandMap.getCommand("PrintContract")).Run(in));
  }
  // NEW_COMMAND_BOILERPLATE

}
