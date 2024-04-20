package com.workup.contracts.commands;

import com.workup.contracts.models.TerminationRequest;
import com.workup.shared.commands.contracts.requests.ContractTerminationRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.enums.contracts.TerminationRequestStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RequestContractTerminationCommand extends ContractCommand<ContractTerminationRequest, ContractTerminationResponse>{

    @Override
    public ContractTerminationResponse Run(ContractTerminationRequest request)
    {
        //Check if there is a termination request pending for this user on that contract
        List<TerminationRequest> existedRequests = terminationRequestRepository.findByRequesterIdAndContractIdAndStatus(request.getUserId(), request.getContractId(), TerminationRequestStatus.PENDING);
        if(!existedRequests.isEmpty())
            return ContractTerminationResponse
                    .builder()
                    .withRequestStatus(existedRequests.getFirst().getStatus())
                    .withSuccess(true)
                    .build();
        //create the request
        TerminationRequest terminationRequest = TerminationRequest
                .builder()
                .withRequestId(UUID.randomUUID())
                .withContractId(request.getContractId())
                .withRequesterId(request.getUserId())
                .withReason(request.getReason())
                .withStatus(TerminationRequestStatus.PENDING)
                .withDate(new Date())
                .build();

        try{
            TerminationRequest savedRequest = terminationRequestRepository.save(terminationRequest);

            System.out.println(" [x] Saved Termination Request '" + savedRequest) ;

            return ContractTerminationResponse.builder()
                    .withRequestStatus(savedRequest.getStatus())
                    .withSuccess(true)
                    .build();
        }catch(Exception e){
            e.printStackTrace();
            return ContractTerminationResponse.builder()
                    .withSuccess(false)
                    .build();
        }
    }
}
