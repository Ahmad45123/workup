package com.workup.webserver.controller;

import com.workup.shared.commands.contracts.requests.ContractTerminationRequest;
import com.workup.shared.commands.contracts.requests.EvaluateMilestoneRequest;
import com.workup.shared.commands.contracts.requests.GetContractRequest;
import com.workup.shared.commands.contracts.requests.GetMilestoneRequest;
import com.workup.shared.commands.contracts.requests.GetPendingTerminationsRequest;
import com.workup.shared.commands.contracts.requests.HandleTerminationRequest;
import com.workup.shared.commands.contracts.requests.ProgressMilestoneRequest;
import com.workup.shared.commands.contracts.requests.ViewContractMilestonesRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.commands.contracts.responses.EvaluateMilestoneResponse;
import com.workup.shared.commands.contracts.responses.GetContractResponse;
import com.workup.shared.commands.contracts.responses.GetMilestoneResponse;
import com.workup.shared.commands.contracts.responses.GetPendingTerminationsResponse;
import com.workup.shared.commands.contracts.responses.HandleTerminationResponse;
import com.workup.shared.commands.contracts.responses.ProgressMilestoneResponse;
import com.workup.shared.commands.contracts.responses.ViewContractMilestonesResponse;
import com.workup.shared.enums.ServiceQueueNames;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contracts")
public class ContractsController {
  @Autowired AmqpTemplate rabbitTemplate;

  @GetMapping("/{id}")
  public ResponseEntity<GetContractResponse> getContractById(
      @PathVariable String id, @RequestAttribute(name = "userId") String userId) {
    GetContractRequest request =
        GetContractRequest.builder().withContractId(id).withUserId(userId).build();
    GetContractResponse response =
        (GetContractResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @GetMapping("/{id}/milestones")
  public ResponseEntity<ViewContractMilestonesResponse> viewContractMilestones(
      @PathVariable String id, @RequestAttribute(name = "userId") String userId) {
    ViewContractMilestonesRequest request =
        ViewContractMilestonesRequest.builder().withContractId(id).withUserId(userId).build();
    ViewContractMilestonesResponse response =
        (ViewContractMilestonesResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @GetMapping("/milestones/{id}")
  public ResponseEntity<GetMilestoneResponse> getMilestoneById(
      @PathVariable String id, @RequestAttribute(name = "userId") String userId) {
    GetMilestoneRequest request =
        GetMilestoneRequest.builder().withMilestoneId(id).withUserId(userId).build();
    GetMilestoneResponse response =
        (GetMilestoneResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @GetMapping("/{id}/terminations")
  public ResponseEntity<GetPendingTerminationsResponse> getContractTermination(
      @PathVariable String id, @RequestAttribute(name = "userId") String userId) {
    GetPendingTerminationsRequest request =
        GetPendingTerminationsRequest.builder().withContractId(id).withUserId(userId).build();
    GetPendingTerminationsResponse response =
        (GetPendingTerminationsResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @PostMapping("/{id}/terminations/request")
  public ResponseEntity<ContractTerminationResponse> submitTerminationRequest(
      @RequestBody ContractTerminationRequest request,
      @PathVariable String id,
      @RequestAttribute(name = "userId") String userId) {
    request.setUserId(userId);
    request.setContractId(id);
    ContractTerminationResponse response =
        (ContractTerminationResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @PostMapping("/milestones/{id}/evaluate")
  public ResponseEntity<EvaluateMilestoneResponse> evaluateMilestone(
      @RequestBody EvaluateMilestoneRequest request,
      @PathVariable String id,
      @RequestAttribute(name = "userId") String userId) {
    request.setUserId(userId);
    request.setMilestoneId(id);
    EvaluateMilestoneResponse response =
        (EvaluateMilestoneResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @PostMapping("/terminations/{id}/handle")
  public ResponseEntity<HandleTerminationResponse> handleTerminationRequest(
      @RequestBody HandleTerminationRequest request,
      @PathVariable String id,
      @RequestAttribute(name = "userId") String userId) {
    request.setUserId(userId);
    request.setContractTerminationRequestId(id);
    HandleTerminationResponse response =
        (HandleTerminationResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @PostMapping("/milestones/{id}/progress")
  public ResponseEntity<ProgressMilestoneResponse> progressMilestone(
      @RequestBody ProgressMilestoneRequest request,
      @PathVariable String id,
      @RequestAttribute(name = "userId") String userId) {
    request.setUserId(userId);
    request.setMilestoneId(id);
    ProgressMilestoneResponse response =
        (ProgressMilestoneResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }
}
