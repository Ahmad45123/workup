package com.workup.contracts.commands;

import com.workup.contracts.models.Contract;
import com.workup.contracts.models.ContractMilestone;
import com.workup.shared.commands.contracts.requests.PrintContractRequest;
import com.workup.shared.commands.contracts.responses.PrintContractResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.Optional;

import java.util.UUID;

public class PrintContractCommand
    extends ContractCommand<PrintContractRequest, PrintContractResponse> {

  @Override
  public PrintContractResponse Run(PrintContractRequest request) {
    // Get Contract by ID provided from the request

    Optional<Contract> contract = contractRepository.findById(
        UUID.fromString(request.getContractId()));

    // If the contract is not found, we will return an error message
    
    if (contract.isEmpty()) {
      return PrintContractResponse
          .builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("No Contract With This ID Is Found!")
          .build();
    }

    // If the contract is found, we will print it and return the contract string

    String contractString = printContract(contract.get());
    return PrintContractResponse
        .builder()
        .withStatusCode(HttpStatusCode.FOUND)
        .withErrorMessage("No Termination Requests Found")
        .withContractFileLink(contractString)
        .build();
  }

  public String printContract(Contract contract) {
    
    StringBuilder contractString = new StringBuilder();
    contractString.append("Contract ID: " + contract.getContractId() + "\n");
    contractString.append("Contract Status: " + contract.getStatus() + "\n");
    contractString.append("Contract Freelancer ID: " + contract.getFreelancerId() + "\n");
    contractString.append("Contract Client ID: " + contract.getClientId() + "\n");
    contractString.append("Contract Job ID: " + contract.getJobId() + "\n");
    contractString.append("Contract Proposal ID: " + contract.getProposalId() + "\n");
    contractString.append("Contract Job Title: " + contract.getJobTitle() + "\n");
    contractString.append("Contract Milestones: \n");

    // Print all the milestones of the contract

    for (String milestoneId : contract.getMilestonesIds()) {

      Optional<ContractMilestone> milestone = contractMilestoneRepository.findById(
          UUID.fromString(milestoneId));

      // If the milestone is not found, we will skip it. Should we or should we throw
      // an Error?

      if (milestone.isEmpty()) {
        continue;
      }
      ContractMilestone milestoneData = milestone.get();

      contractString.append("Milestone ID: " + milestoneData.getMilestoneId() + "\n");
      contractString.append("Milestone Contract ID: " + milestoneData.getContractId() + "\n");
      contractString.append("Milestone Description: " + milestoneData.getDescription() + "\n");
      contractString.append("Milestone Due Date: " + milestoneData.getDueDate() + "\n");
      contractString.append("Milestone Amount: " + milestoneData.getAmount() + "\n");
    }

    return contractString.toString();
  }
}
