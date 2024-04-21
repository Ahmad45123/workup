package com.workup.contracts.commands;

import com.workup.contracts.models.TerminationRequest;
import com.workup.shared.commands.contracts.requests.HandleTerminationRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.commands.contracts.responses.HandleTerminationResponse;
import com.workup.shared.enums.contracts.TerminationRequestStatus;

import java.util.Optional;
import java.util.UUID;

public class HandleTerminationRequestCommand extends ContractCommand<HandleTerminationRequest, HandleTerminationResponse> {

    //TODO: validation by authorizing the `adminId` from `user service` and adding `adminId` to request body
    public HandleTerminationResponse Run(HandleTerminationRequest request) {
        Optional<TerminationRequest> terminationRequest = terminationRequestRepository.findById(UUID.fromString(request.getContractTerminationRequestId()));
        if (terminationRequest.isEmpty()) {
            return HandleTerminationResponse.builder().withSuccess(false).build();
        }

        return handleTerminationRequest(terminationRequest.get(), request.getChosenStatus());
    }

    private HandleTerminationResponse handleTerminationRequest(TerminationRequest terminationRequest, TerminationRequestStatus newStatus) {
        terminationRequest.setStatus(newStatus);

        try {
            TerminationRequest updatedRequest = terminationRequestRepository.save(terminationRequest);

            System.out.println(" [x] Updated Termination Request " + updatedRequest);

            return HandleTerminationResponse.builder().withRequestStatus(updatedRequest.getStatus()).withSuccess(true).build();
        } catch (Exception e) {
            e.printStackTrace();
            return HandleTerminationResponse.builder().withSuccess(false).build();
        }
    }

}
