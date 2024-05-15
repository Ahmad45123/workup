package com.workup.contracts.tests;

import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.logger.LoggingLevel;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.enums.ServiceQueueNames;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.amqp.core.AmqpTemplate;

public class InitiateContractTests {

  public static void initiateContractTest1(AmqpTemplate template) throws ParseException {
    ContractsLogger.print("[ ] Running InitiateContractTest1...", LoggingLevel.TRACE);

    Milestone milestone =
        Milestone.builder()
            .withDescription("make sure the students hate your admin system")
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withAmount(30000)
            .build();

    List<Milestone> milestones = new ArrayList<>();
    milestones.add(milestone);

    InitiateContractRequest initiateContractRequest =
        InitiateContractRequest.builder()
            .withClientId("123")
            .withFreelancerId("456")
            .withJobId("789")
            .withProposalId("bruh")
            .withJobTitle("very happy guc worker :)")
            .withJobMilestones(milestones)
            .build();
    InitiateContractResponse resp =
        (InitiateContractResponse)
            template.convertSendAndReceive(ServiceQueueNames.CONTRACTS, initiateContractRequest);

    assert resp != null : "InitiateContractTest1 Response was null";
    ContractsLogger.print(
        "Response success output: " + resp.getStatusCode() + " " + resp.getErrorMessage(),
        LoggingLevel.TRACE);
    ContractsLogger.print("[x] Finished InitiateContractTest1...", LoggingLevel.TRACE);
  }
}
