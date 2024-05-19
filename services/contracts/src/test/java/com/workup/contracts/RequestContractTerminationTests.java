package com.workup.contracts;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.contracts.repositories.TerminationRequestRepository;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.ContractTerminationRequest;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestContractTerminationTests {

  @Autowired TerminationRequestRepository terminationRequestRepository;

  public void contractNotFoundTest(AmqpTemplate template) {

    ContractTerminationRequest request =
        ContractTerminationRequest.builder()
            .withContractId(UUID.randomUUID().toString())
            .withUserId(UUID.randomUUID().toString())
            .withReason("m4 3agebny el 4o8l da ana")
            .build();

    ContractTerminationResponse response =
        (ContractTerminationResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);
    assertEquals(response.getStatusCode(), HttpStatusCode.BAD_REQUEST);
    assertEquals(response.getErrorMessage(), "The contract is not valid.");
  }

  public void unAuthorizedRequestTest(AmqpTemplate template) throws ParseException {

    // create a contract to exist there
    Milestone milestone =
        Milestone.builder()
            .withDescription("make sure the students hate your admin system")
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withAmount(30000)
            .build();

    List<Milestone> milestones = new ArrayList<>();
    milestones.add(milestone);

    String clientId = UUID.randomUUID().toString(), freelancerId = UUID.randomUUID().toString();
    InitiateContractRequest initiateContractRequest =
        InitiateContractRequest.builder()
            .withClientId(clientId)
            .withFreelancerId(freelancerId)
            .withJobId("789")
            .withProposalId("bruh")
            .withJobTitle("very happy guc worker :)")
            .withJobMilestones(milestones)
            .build();
    InitiateContractResponse contractResponse =
        (InitiateContractResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, initiateContractRequest);

    // now the contract is in db craft termination req
    assertNotNull(contractResponse);

    ContractTerminationRequest request =
        ContractTerminationRequest.builder()
            .withReason("m4 3agebny el 4o8l da")
            .withContractId(contractResponse.getContractId())
            .withUserId(UUID.randomUUID().toString())
            .build();
    ContractTerminationResponse response =
        (ContractTerminationResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    assertNotNull(response);
    assertEquals(response.getStatusCode(), HttpStatusCode.UNAUTHORIZED);
  }

  // TODO: Make a test for the inActive contract (But we don't have a contract status update now)

  public void requestedBeforeTest(AmqpTemplate template) throws ParseException {

    // create a contract to exist there
    Milestone milestone =
        Milestone.builder()
            .withDescription("make sure the students hate your admin system")
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withAmount(30000)
            .build();

    List<Milestone> milestones = new ArrayList<>();
    milestones.add(milestone);

    String clientId = UUID.randomUUID().toString(), freelancerId = UUID.randomUUID().toString();
    InitiateContractRequest initiateContractRequest =
        InitiateContractRequest.builder()
            .withClientId(clientId)
            .withFreelancerId(freelancerId)
            .withJobId("789")
            .withProposalId("bruh")
            .withJobTitle("very happy guc worker :)")
            .withJobMilestones(milestones)
            .build();
    InitiateContractResponse contractResponse =
        (InitiateContractResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, initiateContractRequest);

    assertNotNull(contractResponse);

    ContractTerminationRequest request =
        ContractTerminationRequest.builder()
            .withContractId(contractResponse.getContractId())
            .withReason("M4 3agebny ana el 4o8l da")
            .withUserId(clientId)
            .build();

    ContractTerminationResponse response =
        (ContractTerminationResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);

    ContractTerminationRequest duplicateRequest =
        ContractTerminationRequest.builder()
            .withContractId(contractResponse.getContractId())
            .withReason("M4 3agebny ana el 4o8l da")
            .withUserId(clientId)
            .build();

    ContractTerminationResponse duplicateResponse =
        (ContractTerminationResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, duplicateRequest);

    assertNotNull(duplicateResponse);
    assertEquals(duplicateResponse.getStatusCode(), HttpStatusCode.BAD_REQUEST);
    assertEquals(duplicateResponse.getErrorMessage(), "Termination Request already exists");
  }

  public void sucessTest(AmqpTemplate template) throws ParseException {

    // create a contract to exist there
    Milestone milestone =
        Milestone.builder()
            .withDescription("make sure the students hate your admin system")
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withAmount(30000)
            .build();

    List<Milestone> milestones = new ArrayList<>();
    milestones.add(milestone);

    String clientId = UUID.randomUUID().toString(), freelancerId = UUID.randomUUID().toString();
    InitiateContractRequest initiateContractRequest =
        InitiateContractRequest.builder()
            .withClientId(clientId)
            .withFreelancerId(freelancerId)
            .withJobId("789")
            .withProposalId("bruh")
            .withJobTitle("very happy guc worker :)")
            .withJobMilestones(milestones)
            .build();
    InitiateContractResponse contractResponse =
        (InitiateContractResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, initiateContractRequest);

    assertNotNull(contractResponse);

    ContractTerminationRequest request =
        ContractTerminationRequest.builder()
            .withUserId(clientId)
            .withContractId(contractResponse.getContractId())
            .withReason("M4 3agebni l 4o8l da")
            .build();

    ContractTerminationResponse response =
        (ContractTerminationResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);
    assertEquals(response.getStatusCode(), HttpStatusCode.CREATED);

    terminationRequestRepository
        .findById(UUID.fromString(response.getRequestId()))
        .ifPresentOrElse(
            x -> assertEquals(x.getStatus(), TerminationRequestStatus.PENDING),
            () -> new RuntimeException("Not inserted and test failed"));
  }
}
