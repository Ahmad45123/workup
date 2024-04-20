package com.workup.contracts.commands;

import com.workup.contracts.models.TerminationRequest;
import com.workup.shared.commands.contracts.requests.HandleTerminationRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.commands.contracts.responses.HandleTerminationResponse;

import java.util.Optional;
import java.util.UUID;

public class HandleTerminationRequestCommand extends ContractCommand<HandleTerminationRequest, HandleTerminationResponse> {

    /*
        Validation is needed here :
            I think best way to validate is to have the adminId in the request and talk to the Users mini-service
            and check if he is a valid admin, to be able to handle the request.
     */
    public HandleTerminationResponse Run(HandleTerminationRequest request)
    {
        Optional<TerminationRequest> optionalTerminationRequest = terminationRequestRepository.findById(UUID.fromString(request.getContractTerminationRequestId()));
        if(optionalTerminationRequest.isEmpty())
        {
            //Invalid request because the contract id is invalid
            return HandleTerminationResponse.builder().withSuccess(false).build();
        }
        //Update the status based on the acc/rej choice
        TerminationRequest terminationRequest = optionalTerminationRequest.get();
        terminationRequest.setStatus(request.getChosenStatus());

        try{
            TerminationRequest updatedRequest = terminationRequestRepository.save(terminationRequest);

            System.out.println(" [x] Updated Termination Request '" + updatedRequest) ;

            return HandleTerminationResponse.builder()
                    .withRequestStatus(updatedRequest.getStatus())
                    .withSuccess(true)
                    .build();
        }catch(Exception e){
            e.printStackTrace();
            return HandleTerminationResponse.builder()
                    .withSuccess(false)
                    .build();
        }
    }

}
