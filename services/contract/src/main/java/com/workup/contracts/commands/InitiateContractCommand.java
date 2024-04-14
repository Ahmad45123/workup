package com.workup.contracts.commands;

import java.util.UUID;

import com.workup.contracts.models.Contract;
import com.workup.shared.commands.*;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;

public class InitiateContractCommand extends ContractCommand<InitiateContractRequest> {

    @Override
    public void Run(InitiateContractRequest request) {
//        Contract contract = Contract.builder()
//                .withContractId()Id(UUID.randomUUID())
//                .withTitle(request.getTitle())
//                .withDescription(request.getDescription())
//                .withExperienceLevel(request.getExperience())
//                .build();
//        try{
//            Contract savedJob = jobRepository.save(job);
//            System.out.println(" [x] Saved Job '" + savedJob.getTitle()) ;
//        }catch(Exception e){
//            e.printStackTrace();
//        }

        System.out.println("Empty command for now");
    }

}
