package com.workup.contracts.tests;

import com.workup.shared.commands.contracts.requests.ContractTerminationRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import org.springframework.amqp.core.AmqpTemplate;

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
}
