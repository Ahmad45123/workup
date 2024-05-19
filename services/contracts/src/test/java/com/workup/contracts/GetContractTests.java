package com.workup.contracts;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.contracts.models.Contract;
import com.workup.contracts.repositories.ContractRepository;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.GetContractRequest;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.GetContractResponse;
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
public class GetContractTests {

  @Autowired ContractRepository contractRepository;

  public void contractNotFoundTest(AmqpTemplate template) {
    GetContractRequest request =
        GetContractRequest.builder().withContractId(UUID.randomUUID().toString()).build();

    GetContractResponse response =
        (GetContractResponse) template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    assertNotNull(response);
    assertEquals(HttpStatusCode.NOT_FOUND, response.getStatusCode());
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

    assertNotNull(contractResponse);

    GetContractRequest request =
        GetContractRequest.builder().withContractId(contractResponse.getContractId()).build();

    GetContractResponse response =
        (GetContractResponse) template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());

    Optional<Contract> contractOptional =
        contractRepository.findById(UUID.fromString(response.getContractId()));
    if (contractOptional.isEmpty()) fail();

    Contract contract = contractOptional.get();

    assertEquals("789", contract.getJobId());
    assertEquals("bruh", contract.getProposalId());
    assertEquals("very happy guc worker :)", contract.getJobTitle());
    assertEquals(clientId, contract.getClientId());
    assertEquals(freelancerId, contract.getFreelancerId());
  }
}
