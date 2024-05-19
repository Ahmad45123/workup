package com.workup.contracts;

import com.workup.contracts.commands.*;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.contracts.requests.*;
import com.workup.shared.commands.contracts.requests.ContractTerminationRequest;
import com.workup.shared.commands.contracts.requests.EvaluateMilestoneRequest;
import com.workup.shared.commands.contracts.requests.GetContractRequest;
import com.workup.shared.commands.contracts.requests.GetPendingTerminationsRequest;
import com.workup.shared.commands.contracts.requests.HandleTerminationRequest;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.requests.MarkPaymentCompletedRequest;
import com.workup.shared.commands.contracts.requests.ProgressMilestoneRequest;
import com.workup.shared.commands.contracts.requests.ViewContractMilestonesRequest;
import com.workup.shared.commands.contracts.responses.*;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.commands.contracts.responses.EvaluateMilestoneResponse;
import com.workup.shared.commands.contracts.responses.GetContractResponse;
import com.workup.shared.commands.contracts.responses.GetPendingTerminationsResponse;
import com.workup.shared.commands.contracts.responses.HandleTerminationResponse;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.commands.contracts.responses.MarkPaymentCompletedResponse;
import com.workup.shared.commands.contracts.responses.ProgressMilestoneResponse;
import com.workup.shared.commands.contracts.responses.ViewContractMilestonesResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import java.util.concurrent.CompletableFuture;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.CONTRACTS, id = ServiceQueueNames.CONTRACTS)
public class RabbitMQListener {

  @Autowired public ContractCommandMap commandMap;

  @RabbitHandler
  @Async
  public CompletableFuture<InitiateContractResponse> receive(InitiateContractRequest in)
      throws Exception {
    try {
      InitiateContractResponse response =
          (InitiateContractResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("InitiateContract")).Run(in);
      return CompletableFuture.completedFuture(response);
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          InitiateContractResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ContractTerminationResponse> receive(ContractTerminationRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (ContractTerminationResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("RequestContractTermination"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          ContractTerminationResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<HandleTerminationResponse> receive(HandleTerminationRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (HandleTerminationResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("HandleTerminationRequest"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          HandleTerminationResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<MarkPaymentCompletedResponse> receive(MarkPaymentCompletedRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (MarkPaymentCompletedResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("MarkMilestoneAsPaid")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          MarkPaymentCompletedResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ViewContractMilestonesResponse> receive(ViewContractMilestonesRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (ViewContractMilestonesResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("ViewContractMilestones"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          ViewContractMilestonesResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetContractResponse> receive(GetContractRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetContractResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetContract")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetContractResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<EvaluateMilestoneResponse> receive(EvaluateMilestoneRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (EvaluateMilestoneResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("EvaluateMilestone")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          EvaluateMilestoneResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ProgressMilestoneResponse> receive(ProgressMilestoneRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (ProgressMilestoneResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("ProgressMilestone")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          ProgressMilestoneResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetPendingTerminationsResponse> receive(GetPendingTerminationsRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetPendingTerminationsResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetPendingTerminations"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetPendingTerminationsResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
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
