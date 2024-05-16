package com.workup.contracts;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.workup.contracts.models.Contract;
import com.workup.contracts.models.ContractMilestone;
import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.contracts.repositories.ContractRepository;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitiateContractTests {

  @Autowired ContractRepository contractRepository;

  @Autowired ContractMilestoneRepository contractMilestoneRepository;

  public void successTest(AmqpTemplate template) throws ParseException {
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
    InitiateContractResponse response =
        (InitiateContractResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, initiateContractRequest);

    assertNotNull(response);

    // Now make sure that this contract is exactly the one submitted
    assertEquals(HttpStatusCode.CREATED, response.getStatusCode());

    Optional<Contract> contractOptional =
        contractRepository.findById(UUID.fromString(response.getContractId()));
    if (contractOptional.isEmpty()) fail();

    Contract contract = contractOptional.get();

    assertEquals("789", contract.getJobId());
    assertEquals("bruh", contract.getProposalId());
    assertEquals("very happy guc worker :)", contract.getJobTitle());
    assertEquals(clientId, contract.getClientId());
    assertEquals(freelancerId, contract.getFreelancerId());

    // Now check over the milestone that it was inserted correctly with the contract also
    List<String> milestoneIds = contract.getMilestonesIds();

    Optional<ContractMilestone> optionalContractMilestone =
        contractMilestoneRepository.findById(UUID.fromString(milestoneIds.getFirst()));
    if (optionalContractMilestone.isEmpty())
      fail("Milestones weren't added with the contract correctly");

    ContractMilestone addedMilestone = optionalContractMilestone.get();
    assertEquals("make sure the students hate your admin system", addedMilestone.getDescription());
    assertEquals(
        new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"), addedMilestone.getDueDate());
    assertEquals(30000, addedMilestone.getAmount());
  }
}
