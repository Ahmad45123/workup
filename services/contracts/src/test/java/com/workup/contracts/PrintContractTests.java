package com.workup.contracts;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.contracts.models.Contract;
import com.workup.contracts.models.ContractMilestone;
import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.contracts.repositories.ContractRepository;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.requests.PrintContractRequest;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.commands.contracts.responses.PrintContractResponse;
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
public class PrintContractTests {

  @Autowired ContractRepository contractRepository;

  @Autowired ContractMilestoneRepository contractMilestoneRepository;

  public void contractNotFoundTest(AmqpTemplate template) {
    PrintContractRequest request =
        PrintContractRequest.builder().withContractId(UUID.randomUUID().toString()).build();

    PrintContractResponse response =
        (PrintContractResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    assertNotNull(response);
    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());
  }

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
    InitiateContractResponse contractResponse =
        (InitiateContractResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, initiateContractRequest);

    try {
      Optional<Contract> contract =
          contractRepository.findById(UUID.fromString(contractResponse.getContractId()));
      if (contract.isEmpty()) fail("PrintContract Test has failed");

      String expected = printContract(contract.get());

      PrintContractRequest request =
          PrintContractRequest.builder().withContractId(contractResponse.getContractId()).build();

      PrintContractResponse response =
          (PrintContractResponse)
              template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
      assertNotNull(response);
      assertEquals(HttpStatusCode.OK, response.getStatusCode());
      assertEquals(expected, response.getWithFormatedContractDetails());
    } catch (Exception e) {
      fail("PrintContract Test has failed");
    }
  }

  public String printContract(Contract contract) {

    StringBuilder contractString = new StringBuilder();
    contractString.append("Contract ID: ").append(contract.getContractId()).append("\n");
    contractString.append("Contract Status: ").append(contract.getStatus()).append("\n");
    contractString
        .append("Contract Freelancer ID: ")
        .append(contract.getFreelancerId())
        .append("\n");
    contractString.append("Contract Client ID: ").append(contract.getClientId()).append("\n");
    contractString.append("Contract Job ID: ").append(contract.getJobId()).append("\n");
    contractString.append("Contract Proposal ID: ").append(contract.getProposalId()).append("\n");
    contractString.append("Contract Job Title: ").append(contract.getJobTitle()).append("\n");
    contractString.append("Contract Milestones: \n");

    // Print all the milestones of the contract

    for (String milestoneId : contract.getMilestonesIds()) {

      Optional<ContractMilestone> milestone =
          contractMilestoneRepository.findById(UUID.fromString(milestoneId));

      // If the milestone is not found, we will skip it. Should we or should we throw
      // an Error?
      if (milestone.isEmpty()) {
        continue;
      }
      ContractMilestone milestoneData = milestone.get();

      contractString.append("Milestone ID: ").append(milestoneData.getMilestoneId()).append("\n");
      contractString
          .append("Milestone Contract ID: ")
          .append(milestoneData.getContractId())
          .append("\n");
      contractString
          .append("Milestone Description: ")
          .append(milestoneData.getDescription())
          .append("\n");
      contractString.append("Milestone Due Date: ").append(milestoneData.getDueDate()).append("\n");
      contractString.append("Milestone Amount: ").append(milestoneData.getAmount()).append("\n");
    }

    return contractString.toString();
  }
}
