package com.workup.contracts;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.requests.MarkPaymentCompletedRequest;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.commands.contracts.responses.MarkPaymentCompletedResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.enums.contracts.MilestoneState;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarkMilestoneAsPaidTests {

  @Autowired ContractMilestoneRepository contractMilestoneRepository;

  public void nonExistingMilestone(AmqpTemplate template) throws ParseException {

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

    MarkPaymentCompletedRequest request =
        MarkPaymentCompletedRequest.builder()
            .withMilestoneId(String.valueOf(UUID.randomUUID()))
            .build();

    MarkPaymentCompletedResponse response =
        (MarkPaymentCompletedResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);
    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());
    assertEquals("Milestone is not found", response.getErrorMessage());
  }

  public void nonAcceptedMilestone(AmqpTemplate template) throws ParseException {
    UUID milestoneId = UUID.randomUUID();
    Milestone milestone =
        Milestone.builder()
            .withMilestoneId(String.valueOf(milestoneId))
            .withDescription("make sure the students hate your admin system")
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withAmount(30000)
            .withStatus(MilestoneState.IN_PROGRESS)
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

    MarkPaymentCompletedRequest request =
        MarkPaymentCompletedRequest.builder().withMilestoneId(String.valueOf(milestoneId)).build();

    MarkPaymentCompletedResponse response =
        (MarkPaymentCompletedResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);
    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());
    assertEquals("Milestone is not accepted for payment", response.getErrorMessage());
  }

  public void successTest(AmqpTemplate template) throws ParseException {
    UUID milestoneId = UUID.randomUUID();
    Milestone milestone =
        Milestone.builder()
            .withMilestoneId(String.valueOf(milestoneId))
            .withDescription("make sure the students hate your admin system")
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withAmount(30000)
            .withStatus(MilestoneState.ACCEPTED)
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

    MarkPaymentCompletedRequest request =
        MarkPaymentCompletedRequest.builder().withMilestoneId(String.valueOf(milestoneId)).build();

    MarkPaymentCompletedResponse response =
        (MarkPaymentCompletedResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());

    contractMilestoneRepository
        .findById(milestoneId)
        .ifPresentOrElse(
            x -> assertEquals(MilestoneState.PAID, x.getStatus()),
            () -> new RuntimeException("Milestone not found"));
  }
}
