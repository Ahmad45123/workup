package com.workup.contracts.commands;

import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.models.Contract;
import com.workup.contracts.models.ContractMilestone;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.contracts.ContractState;
import com.workup.shared.enums.contracts.MilestoneState;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InitiateContractCommand
    extends ContractCommand<InitiateContractRequest, InitiateContractResponse> {

  @Override
  public InitiateContractResponse Run(InitiateContractRequest request) {
    // First we will get the milestones and add them to the database first,
    // This will allow us to have their IDs for when we insert the contract.
    int milestonesCount = request.getJobMilestones().size();
    ArrayList<ContractMilestone> milestonesToAdd = new ArrayList<>();
    List<String> milestoneIds = new ArrayList<>();

    final UUID contractId = UUID.randomUUID();
    for (Milestone m : request.getJobMilestones()) {
      UUID currentContractMilestoneId =
          m.getMilestoneId() != null ? UUID.fromString(m.getMilestoneId()) : UUID.randomUUID();
      MilestoneState status = m.getStatus() != null ? m.getStatus() : MilestoneState.OPEN;

      ContractMilestone contractMilestone =
          ContractMilestone.builder()
              .withMilestoneId(currentContractMilestoneId)
              .withAmount(m.getAmount())
              .withDescription(m.getDescription())
              .withContractId(contractId.toString())
              .withDueDate(m.getDueDate())
              .withStatus(status)
              .build();

      milestoneIds.add(currentContractMilestoneId.toString());
      milestonesToAdd.add(contractMilestone);
    }

    // Then we will add the contract to the database
    Contract contract =
        Contract.builder()
            .withContractId(contractId)
            .withJobId(request.getJobId())
            .withJobTitle(request.getJobTitle())
            .withClientId(request.getClientId())
            .withFreelancerId(request.getFreelancerId())
            .withMilestonesIds(milestoneIds)
            .withProposalId(request.getProposalId())
            .withStatus(ContractState.ACTIVE)
            .build();
    try {
      Contract savedContract = contractRepository.save(contract);

      ContractsLogger.print(" [x] Saved Contract '" + savedContract.getJobTitle());

      contractMilestoneRepository.saveAll(milestonesToAdd);

      ContractsLogger.print(" [x] Saved All Milestones '" + savedContract.getJobTitle());

      return InitiateContractResponse.builder()
          .withContractId(savedContract.getContractId().toString())
          .withStatusCode(HttpStatusCode.CREATED)
          .withContractId(savedContract.getContractId().toString())
          .withErrorMessage("")
          .build();
    } catch (Exception e) {
      e.printStackTrace();
      return InitiateContractResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage(e.getMessage())
          .build();
    }
  }
}
