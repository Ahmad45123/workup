package com.workup.contracts.commands;

import com.workup.contracts.repositories.ContractRepository;
import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;

public abstract class ContractCommand<T extends CommandRequest, Q extends CommandResponse> implements Command<T, Q>{
    ContractRepository contractRepository;
    ContractMilestoneRepository contractMilestoneRepository;


    public void setContractRepository(ContractRepository ContractRepository) {
        this.contractRepository = contractRepository;
    }

    public void setContractMilestoneRepository(ContractMilestoneRepository ContractMilestoneRepository) {
        this.contractMilestoneRepository = ContractMilestoneRepository;
    }


}
