package com.workup.contracts.commands;

import com.workup.contracts.repositories.ContractRepository;
import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.shared.commands.CommandMap;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractCommandMap extends CommandMap<ContractCommand<? extends CommandRequest, ? extends CommandResponse>> {
    @Autowired
    ContractRepository contractRepository;
    ContractMilestoneRepository contractMilestoneRepository;


    public void registerCommands() {

        commands.put("InitiateContract", InitiateContractCommand.class);
        // NEW_COMMAND_BOILERPLATE
    }

    @Override
    public void setupCommand(ContractCommand<? extends CommandRequest, ? extends CommandResponse> command) {
        command.setContractRepository(contractRepository);
        command.setContractMilestoneRepository(contractMilestoneRepository);
    }
}
