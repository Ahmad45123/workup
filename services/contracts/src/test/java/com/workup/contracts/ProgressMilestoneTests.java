package com.workup.contracts;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.contracts.models.Contract;
import com.workup.contracts.models.ContractMilestone;
import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.requests.ProgressMilestoneRequest;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.commands.contracts.responses.ProgressMilestoneResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.enums.contracts.MilestoneState;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProgressMilestoneTests {

  @Autowired ContractMilestoneRepository contractMilestoneRepository;

  public void milestoneNotFoundTest(AmqpTemplate template) {

    // Send the request with random milestone and it should not be found
    ProgressMilestoneRequest request =
        ProgressMilestoneRequest.builder()
            .withMilestoneId(String.valueOf(UUID.randomUUID()))
            .build();

    ProgressMilestoneResponse response =
        (ProgressMilestoneResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);

    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());
    assertEquals("Milestone is not found", response.getErrorMessage());
  }

  public void wrongMilestoneState1(AmqpTemplate template) {
    // Create a Milestone and add it to the db and then check this milestone
    // In progress (not ready for evaluation)
    String milestoneId = UUID.randomUUID().toString();

    ProgressMilestoneRequest request =
        ProgressMilestoneRequest.builder().withMilestoneId(milestoneId).build();

    ContractMilestone dbMilestone =
        ContractMilestone.builder()
            .withMilestoneId(UUID.fromString(milestoneId))
            .withContractId(UUID.randomUUID().toString())
            .withAmount(500)
            .withDescription("Backend Boi")
            .withDueDate(new Date())
            .withStatus(MilestoneState.IN_REVIEW)
            .build();

    contractMilestoneRepository.save(dbMilestone);

    ProgressMilestoneResponse response =
        (ProgressMilestoneResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);
    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());
    assertEquals("Milestone cannot be progressed through this command", response.getErrorMessage());
  }

  public void wrongMilestoneState2(AmqpTemplate template) {
    // Create a Milestone and add it to the db and then check this milestone
    // In progress (not ready for evaluation)
    String milestoneId = UUID.randomUUID().toString();

    ProgressMilestoneRequest request =
        ProgressMilestoneRequest.builder().withMilestoneId(milestoneId).build();

    ContractMilestone dbMilestone =
        ContractMilestone.builder()
            .withMilestoneId(UUID.fromString(milestoneId))
            .withContractId(UUID.randomUUID().toString())
            .withAmount(500)
            .withDescription("Backend Boi")
            .withDueDate(new Date())
            .withStatus(MilestoneState.ACCEPTED)
            .build();

    contractMilestoneRepository.save(dbMilestone);

    ProgressMilestoneResponse response =
        (ProgressMilestoneResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);
    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());
    assertEquals("Milestone cannot be progressed through this command", response.getErrorMessage());
  }

  public void wrongMilestoneState3(AmqpTemplate template) {
    // Create a Milestone and add it to the db and then check this milestone
    // In progress (not ready for evaluation)
    String milestoneId = UUID.randomUUID().toString();

    ProgressMilestoneRequest request =
        ProgressMilestoneRequest.builder().withMilestoneId(milestoneId).build();

    ContractMilestone dbMilestone =
        ContractMilestone.builder()
            .withMilestoneId(UUID.fromString(milestoneId))
            .withContractId(UUID.randomUUID().toString())
            .withAmount(500)
            .withDescription("Backend Boi")
            .withDueDate(new Date())
            .withStatus(MilestoneState.PAID)
            .build();

    contractMilestoneRepository.save(dbMilestone);

    ProgressMilestoneResponse response =
        (ProgressMilestoneResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);
    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());
    assertEquals("Milestone cannot be progressed through this command", response.getErrorMessage());
  }


  public void successTest1(AmqpTemplate template) throws ParseException {
    // create a milestone with it's contract, and evaluate this milestone
    String milestoneId = UUID.randomUUID().toString();
    Milestone milestone =
        Milestone.builder()
            .withMilestoneId(milestoneId)
            .withDescription("make sure the students hate your admin system")
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withAmount(30000)
            .withStatus(MilestoneState.OPEN)
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

    ProgressMilestoneRequest request =
        ProgressMilestoneRequest.builder().withMilestoneId(milestoneId).build();

    ProgressMilestoneResponse response =
        (ProgressMilestoneResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());

    // Check same milestone has the right state

    Optional<ContractMilestone> milestoneInRepoOptional =  contractMilestoneRepository.findById(UUID.fromString(milestoneId));

    ContractMilestone milestoneInRepo = milestoneInRepoOptional.get();
    assertEquals(milestoneInRepo.getStatus(),MilestoneState.IN_PROGRESS);
  }

  public void successTest2(AmqpTemplate template) throws ParseException {
    // create a milestone with it's contract, and evaluate this milestone
    String milestoneId = UUID.randomUUID().toString();
    Milestone milestone =
            Milestone.builder()
                    .withMilestoneId(milestoneId)
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

    ProgressMilestoneRequest request =
            ProgressMilestoneRequest.builder().withMilestoneId(milestoneId).build();

    ProgressMilestoneResponse response =
            (ProgressMilestoneResponse)
                    template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());

    // Check same milestone has the right state

    Optional<ContractMilestone> milestoneInRepoOptional =  contractMilestoneRepository.findById(UUID.fromString(milestoneId));

    ContractMilestone milestoneInRepo = milestoneInRepoOptional.get();
    assertEquals(milestoneInRepo.getStatus(),MilestoneState.IN_REVIEW);
  }

}
