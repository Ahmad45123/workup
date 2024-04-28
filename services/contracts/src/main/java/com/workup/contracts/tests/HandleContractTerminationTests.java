package com.workup.contracts.tests;

import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.ContractTerminationRequest;
import com.workup.shared.commands.contracts.requests.HandleTerminationRequest;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.commands.contracts.responses.HandleTerminationResponse;
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

public class HandleContractTerminationTests {

  public void requestNotFoundTest(AmqpTemplate template) {
    System.out.println("[ ] Running HandleContractTermination Request NotFound Test...");

    HandleTerminationRequest request =
        HandleTerminationRequest.builder()
            .withContractTerminationRequestId(UUID.randomUUID().toString())
            .withChosenStatus(TerminationRequestStatus.REJECTED)
            .build();

    HandleTerminationResponse response =
        (HandleTerminationResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    assert response != null;
    if (response.getErrorMessage().equals("No Termination Requests Found"))
      System.out.println(" [x] Request Not Found Test has Passed");
    else System.out.println(" [x] Request Not Found Test has Failed");

    System.out.println("[x] Finished RequestContractTermination Request NotFound Test .....\n");
  }

  // TODO: Add a test for checking if the contract is not active before applying termination AFTER
  // HAVING ENDPOINT FOR UPDATING CONTRACT STATUS

  public void successTest(AmqpTemplate template) throws ParseException {
    System.out.println("[ ] Running HandleContractTermination Success Test...");
    // create a contract
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
    assert contractResponse != null;

    // create termination request
    String terminationRequestID = UUID.randomUUID().toString();
    ContractTerminationRequest terminationRequest =
        ContractTerminationRequest.builder()
            .withUserId(clientId)
            .withContractId(contractResponse.getContractId())
            .withReason("M4 3agebni l 4o8l da")
            .build();

    ContractTerminationResponse terminationResponse =
        (ContractTerminationResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, terminationRequest);
    assert terminationResponse != null;

    // check over the functionality
    HandleTerminationRequest request =
        HandleTerminationRequest.builder()
            .withChosenStatus(TerminationRequestStatus.ACCEPTED)
            .withContractTerminationRequestId(terminationResponse.getRequestId())
            .build();

    HandleTerminationResponse response =
        (HandleTerminationResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    assert response != null;
    if (response.getStatusCode() == HttpStatusCode.OK) {
      System.out.println(" [x] Success Test has Passed");
    } else System.out.println(" [x] Success Test has Failed");

    System.out.println("[x] Finished RequestContractTermination Success Test .....\n");
  }
}
