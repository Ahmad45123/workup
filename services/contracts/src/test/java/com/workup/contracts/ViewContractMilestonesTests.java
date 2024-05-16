package com.workup.contracts;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.contracts.models.ContractMilestone;
import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.ViewContractMilestonesRequest;
import com.workup.shared.commands.contracts.responses.ViewContractMilestonesResponse;
import com.workup.shared.enums.ServiceQueueNames;
import java.util.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViewContractMilestonesTests {
  @Autowired ContractMilestoneRepository contractMilestoneRepository;

  public void successTest(AmqpTemplate template) {
    String contractId = UUID.randomUUID().toString();
    Random rand = new Random();
    // create some milestones to be added to the db with same contractId
    ArrayList<ContractMilestone> milestones = new ArrayList<>();
    for (int i = 0; i < 10; ++i)
      milestones.add(
          ContractMilestone.builder()
              .withDueDate(new Date())
              .withAmount(rand.nextInt())
              .withContractId(contractId)
              .withDescription("AISFHAS SAGASV")
              .withMilestoneId(UUID.randomUUID())
              .build());

    contractMilestoneRepository.saveAll(milestones);

    ViewContractMilestonesRequest request =
        ViewContractMilestonesRequest.builder().withContractId(contractId).build();

    ViewContractMilestonesResponse response =
        (ViewContractMilestonesResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);

    assertNotNull(response);

    HashMap<String, ContractMilestone> mapping = new HashMap<>();
    milestones.forEach(m -> mapping.put(m.getMilestoneId().toString(), m));

    for (int i = 0; i < response.getContractMilestones().size(); ++i) {
      Milestone currMilestone = response.getContractMilestones().get(i);
      assertEquals(
          mapping.get(currMilestone.getMilestoneId()).getAmount(), currMilestone.getAmount());
      assertEquals(
          mapping.get(currMilestone.getMilestoneId()).getContractId(),
          currMilestone.getContractId());
      assertEquals(
          mapping.get(currMilestone.getMilestoneId()).getDescription(),
          currMilestone.getDescription());
      assertEquals(
          mapping.get(currMilestone.getMilestoneId()).getDueDate(), currMilestone.getDueDate());
    }
  }
}
