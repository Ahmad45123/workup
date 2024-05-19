package com.workup.contracts;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.contracts.models.ContractMilestone;
import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.EvaluateMilestoneRequest;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.EvaluateMilestoneResponse;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.enums.contracts.MilestoneState;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EvaluateMilestoneTests {

  @Autowired ContractMilestoneRepository contractMilestoneRepository;

  public void milestoneNotFoundTest(AmqpTemplate template) {

    // Send the request with random milestone and it should not be found
    EvaluateMilestoneRequest request =
        EvaluateMilestoneRequest.builder()
            .withMilestoneId(String.valueOf(UUID.randomUUID()))
            .withEvaluatedState(MilestoneState.ACCEPTED)
            .build();

    EvaluateMilestoneResponse response =
        (EvaluateMilestoneResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);

    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());
    assertEquals("Milestone is not found", response.getErrorMessage());
  }

  public void wrongMilestoneState(AmqpTemplate template) {
    // Create a Milestone and add it to the db and then check this milestone
    // In progress (not ready for evaluation)
    String milestoneId = UUID.randomUUID().toString();

    EvaluateMilestoneRequest request =
        EvaluateMilestoneRequest.builder()
            .withMilestoneId(milestoneId)
            .withEvaluatedState(MilestoneState.ACCEPTED)
            .build();

    ContractMilestone dbMilestone =
        ContractMilestone.builder()
            .withMilestoneId(UUID.fromString(milestoneId))
            .withContractId(UUID.randomUUID().toString())
            .withAmount(500)
            .withDescription("Backend Boi")
            .withDueDate(new Date())
            .withStatus(MilestoneState.IN_PROGRESS)
            .build();

    contractMilestoneRepository.save(dbMilestone);

    EvaluateMilestoneResponse response =
        (EvaluateMilestoneResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);
    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());
    assertEquals(
        "Milestone cannot be evaluated as it has not progressed enough",
        response.getErrorMessage());
  }

  // TODO: @Karim ElMosallamy
  public void successTest(AmqpTemplate template) throws ParseException {
    // create a milestone with it's contract, and evaluate this milestone
    String milestoneId = UUID.randomUUID().toString();
    Milestone milestone =
        Milestone.builder()
            .withMilestoneId(milestoneId)
            .withDescription("make sure the students hate your admin system")
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withAmount(30000)
            .withStatus(MilestoneState.IN_REVIEW)
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

    EvaluateMilestoneRequest request =
        EvaluateMilestoneRequest.builder()
            .withMilestoneId(milestoneId)
            .withEvaluatedState(MilestoneState.ACCEPTED)
            .build();

    EvaluateMilestoneResponse response =
        (EvaluateMilestoneResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());
  }
}
