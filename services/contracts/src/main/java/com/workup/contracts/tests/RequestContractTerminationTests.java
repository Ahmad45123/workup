package com.workup.contracts.tests;

import com.workup.contracts.repositories.ContractRepository;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.ContractTerminationRequest;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RequestContractTerminationTests {

    public void contractNotFoundTest(AmqpTemplate template)
    {
        System.out.println("[ ] Running RequestContractTermination ContractNotFoundTest .....");
        ContractTerminationRequest request = ContractTerminationRequest.builder()
                .withContractId(UUID.randomUUID().toString())
                .withUserId(UUID.randomUUID().toString())
                .withReason("m4 3agebny el 4o8l da ana")
                .build();

        ContractTerminationResponse response = (ContractTerminationResponse) template.convertSendAndReceive("contractsqueue", request);

        if(response == null)
        {
            System.out.println("Problem has occured in the Contract Not Found Test");
            return;
        }

        assert response.getErrorMessage().equals("The contract is not valid.") : "Contract Not Found Test has Failed";
        System.out.println(" [x] Contract Not Found Test has Passed");
        System.out.println("[x] Finished RequestContractTermination Contract Not Found test .....");
    }

    public void unAuthorizedRequestTest(AmqpTemplate template)
    {
        System.out.println("[ ] Running RequestContractTermination UnAuthorizedRequestTest .....");

        //create a contract to exist there
        Milestone milestone = Milestone
                .builder()
                .withDescription("make sure the students hate your admin system")
                .withDueDate("2025-01-01")
                .withAmount("30000")
                .build();

        List<Milestone> milestones = new ArrayList<>();
        milestones.add(milestone);

        String clientId = UUID.randomUUID().toString(), freelancerId = UUID.randomUUID().toString();
        InitiateContractRequest initiateContractRequest = InitiateContractRequest
                .builder()
                .withClientId(clientId)
                .withFreelancerId(freelancerId)
                .withJobId("789")
                .withProposalId("bruh")
                .withJobTitle("very happy guc worker :)")
                .withJobMilestones(milestones)
                .build();
        InitiateContractResponse contractResponse = (InitiateContractResponse) template.convertSendAndReceive(
                "contractsqueue",
                initiateContractRequest
        );

        //now the contract is in db craft termination req
        assert contractResponse != null;
        ContractTerminationRequest request = ContractTerminationRequest.builder()
                .withReason("m4 3agebny el 4o8l da")
                .withContractId(contractResponse.getContractId())
                .withUserId(UUID.randomUUID().toString())
                .build();
        ContractTerminationResponse response = (ContractTerminationResponse) template.convertSendAndReceive("contractsqueue", request);
        assert Objects.requireNonNull(response).getErrorMessage().equals("The requester is not a part of the contract");
        System.out.println(" [x] UnAuthorized TerminationRequest Test has Passed");
        System.out.println("[x] Finished RequestContractTermination Unauthorized Termination Request test .....");
    }

    //TODO: Make a test for the inActive contract (But we don't have a contract status update now)

    public void requestedBeforeTest(AmqpTemplate template)
    {
        System.out.println("[ ] Running RequestContractTermination Requested Before Test .....");

        //create a contract to exist there
        Milestone milestone = Milestone
                .builder()
                .withDescription("make sure the students hate your admin system")
                .withDueDate("2025-01-01")
                .withAmount("30000")
                .build();

        List<Milestone> milestones = new ArrayList<>();
        milestones.add(milestone);

        String clientId = UUID.randomUUID().toString(), freelancerId = UUID.randomUUID().toString();
        InitiateContractRequest initiateContractRequest = InitiateContractRequest
                .builder()
                .withClientId(clientId)
                .withFreelancerId(freelancerId)
                .withJobId("789")
                .withProposalId("bruh")
                .withJobTitle("very happy guc worker :)")
                .withJobMilestones(milestones)
                .build();
        InitiateContractResponse contractResponse = (InitiateContractResponse) template.convertSendAndReceive(
                "contractsqueue",
                initiateContractRequest
        );

        assert contractResponse != null;

        ContractTerminationRequest request = ContractTerminationRequest.builder().withContractId(contractResponse.getContractId()).withReason("M4 3agebny ana el 4o8l da")
                .withUserId(UUID.randomUUID().toString()).build();

        ContractTerminationResponse response = (ContractTerminationResponse) template.convertSendAndReceive("contractsqueue", request);
        assert response != null;

        ContractTerminationRequest duplicateRequest = ContractTerminationRequest.builder().withContractId(contractResponse.getContractId()).withReason("M4 3agebny ana el 4o8l da")
                .withUserId(UUID.randomUUID().toString()).build();

        ContractTerminationResponse duplicateResponse = (ContractTerminationResponse) template.convertSendAndReceive("contractsqueue", request);

        assert Objects.requireNonNull(duplicateResponse).getErrorMessage().equals("Termination Request already exists");
        System.out.println(" [x] RequestedBefore TerminationRequest has Passed");
        System.out.println("[x] Finished RequestContractTermination Requested Before test .....");
    }
}
