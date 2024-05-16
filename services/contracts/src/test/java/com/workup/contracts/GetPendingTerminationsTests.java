package com.workup.contracts;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.contracts.models.TerminationRequest;
import com.workup.contracts.repositories.TerminationRequestRepository;
import com.workup.shared.commands.contracts.requests.GetPendingTerminationsRequest;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.GetPendingTerminationsResponse;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetPendingTerminationsTests {

  @Autowired TerminationRequestRepository terminationRequestRepository;

  public void invalidContract(AmqpTemplate template) {
    GetPendingTerminationsRequest request =
        GetPendingTerminationsRequest.builder()
            .withContractId(UUID.randomUUID().toString())
            .build();

    GetPendingTerminationsResponse response =
        (GetPendingTerminationsResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    assertNotNull(response);
    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());
  }

  public void successTest(AmqpTemplate template) {
    // create contract
    String clientId = UUID.randomUUID().toString(), freelancerId = UUID.randomUUID().toString();
    InitiateContractRequest initiateContractRequest =
        InitiateContractRequest.builder()
            .withClientId(clientId)
            .withFreelancerId(freelancerId)
            .withJobId("789")
            .withProposalId("bruh")
            .withJobTitle("very happy guc worker :)")
            .withJobMilestones(new ArrayList<>())
            .build();

    InitiateContractResponse contractResponse =
        (InitiateContractResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, initiateContractRequest);

    assertNotNull(contractResponse);
    assertEquals(HttpStatusCode.CREATED, contractResponse.getStatusCode());

    String request1 = UUID.randomUUID().toString(), request2 = UUID.randomUUID().toString();
    TerminationRequest clientTerminationRequest =
        TerminationRequest.builder()
            .withRequestId(UUID.fromString(request1))
            .withReason("m4 3agebny el 4o8l da1")
            .withContractId(contractResponse.getContractId())
            .withRequesterId(clientId)
            .withStatus(TerminationRequestStatus.PENDING)
            .build();

    TerminationRequest freelancerTerminationRequest =
        TerminationRequest.builder()
            .withRequestId(UUID.fromString(request2))
            .withReason("m4 3agebny el 4o8l da2")
            .withContractId(contractResponse.getContractId())
            .withRequesterId(freelancerId)
            .withStatus(TerminationRequestStatus.PENDING)
            .build();

    HashMap<String, TerminationRequest> expectedTerminationRequests = new HashMap<>();
    expectedTerminationRequests.put(freelancerId, freelancerTerminationRequest);
    expectedTerminationRequests.put(clientId, clientTerminationRequest);

    try {
      terminationRequestRepository.save(freelancerTerminationRequest);
      terminationRequestRepository.save(clientTerminationRequest);

      // Now Get the pending requests :D
      GetPendingTerminationsRequest request =
          GetPendingTerminationsRequest.builder()
              .withContractId(contractResponse.getContractId())
              .build();

      GetPendingTerminationsResponse response =
          (GetPendingTerminationsResponse)
              template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

      assertNotNull(response);
      assertEquals(HttpStatusCode.OK, response.getStatusCode());

      response
          .getTerminationRequests()
          .forEach(
              req -> {
                TerminationRequest correspondent =
                    expectedTerminationRequests.get(req.getRequesterId());
                assertEquals(correspondent.getStatus(), req.getStatus());
                assertEquals(correspondent.getContractId(), req.getContractId());
                assertEquals(correspondent.getReason(), req.getReason());
                assertEquals(correspondent.getRequestId().toString(), req.getRequestId());
                assertEquals(correspondent.getRequesterId(), req.getRequesterId());
              });
    } catch (Exception e) {
      fail("Error has occurred in the GetPending TerminationRequests Tests");
    }
  }
}
