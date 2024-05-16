package com.workup.contracts;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.contracts.models.ContractMilestone;
import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.shared.commands.contracts.requests.GetMilestoneRequest;
import com.workup.shared.commands.contracts.responses.GetMilestoneResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetMilestoneTests {

  @Autowired ContractMilestoneRepository contractMilestoneRepository;

  public void milestoneNotFoundTest(AmqpTemplate template) {
    String milestoneId = UUID.randomUUID().toString();
    GetMilestoneRequest request =
        GetMilestoneRequest.builder().withMilestoneId(milestoneId).build();

    GetMilestoneResponse response =
        (GetMilestoneResponse) template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
    System.out.println(response);
    assertNotNull(response);
    assertEquals(HttpStatusCode.NOT_FOUND, response.getStatusCode());
  }

  public void successTest(AmqpTemplate template) throws ParseException {

    String milestoneId = UUID.randomUUID().toString();
    ContractMilestone milestone =
        ContractMilestone.builder()
            .withMilestoneId(UUID.fromString(milestoneId))
            .withDescription("make sure the students hate your admin system")
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withAmount(30000)
            .build();

    try {
      contractMilestoneRepository.save(milestone);

      GetMilestoneRequest request =
          GetMilestoneRequest.builder().withMilestoneId(milestoneId).build();

      GetMilestoneResponse response =
          (GetMilestoneResponse)
              template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, request);
      assertNotNull(response);
      assertEquals(HttpStatusCode.OK, response.getStatusCode());
    } catch (Exception e) {
      fail();
    }
  }
}
