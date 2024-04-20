package com.workup.contracts.commands;

import com.workup.contracts.models.Contract;
import com.workup.contracts.models.TerminationRequest;
import com.workup.shared.commands.contracts.requests.ContractTerminationRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.enums.contracts.TerminationRequestStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RequestContractTerminationCommand extends ContractCommand<ContractTerminationRequest, ContractTerminationResponse>{

    private boolean isValidRequest(ContractTerminationRequest request)
    {
        //check if the contract id is a valid contract
        Optional<Contract> contract = contractRepository.findById(UUID.fromString(request.getContractId()));
        if(contract.isEmpty())
        {
            return false;
        }
        //check that requester id is a part of the contract
        return contract.get().getClientId().equals(request.getUserId())
                || contract.get().getFreelancerId().equals(request.getUserId());

    }

    @Override
    public ContractTerminationResponse Run(ContractTerminationRequest request)
    {
        if(!isValidRequest(request))
        {
            return ContractTerminationResponse.builder().withSuccess(false).build();
        }

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
