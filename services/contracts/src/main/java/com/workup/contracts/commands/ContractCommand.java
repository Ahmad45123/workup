package com.workup.contracts.commands;

import com.workup.contracts.repositories.ContractRepository;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;

public abstract class ContractCommand<T extends CommandRequest> implements Command<T>{
    ContractRepository contractepository;


    public void setJobRepository(ContractRepository jobRepository) {
        this.contractepository = jobRepository;
    }
}
