package com.workup.contracts.tests;

import com.workup.shared.commands.contracts.requests.HandleTerminationRequest;
import com.workup.shared.commands.contracts.responses.HandleTerminationResponse;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.UUID;

public class HandleContractTerminationTests {

    public void requestNotFoundTest(AmqpTemplate template)
    {
        System.out.println("[ ] Running HandleContractTermination Request NotFound Test...");

        HandleTerminationRequest request = HandleTerminationRequest.builder()
                .withContractTerminationRequestId(UUID.randomUUID().toString())
                .withChosenStatus(TerminationRequestStatus.REJECTED)
                .build();

        HandleTerminationResponse response = (HandleTerminationResponse) template.convertSendAndReceive("contractsqueue", request);
        assert response != null;
        if(response.getErrorMessage().equals("No Termination Requests Found"))
            System.out.println(" [x] Request Not Found Test has Passed");
        else
            System.out.println(" [x] Request Not Found Test has Failed");

        System.out.println("[x] Finished RequestContractTermination Request NotFound Test .....");
    }



}
