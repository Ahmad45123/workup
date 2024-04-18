package com.workup.contracts.commands;

import com.workup.contracts.repositories.ContractRepository;
import com.workup.shared.commands.CommandMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractCommandMap extends CommandMap<ContractCommand<?>> {
    @Autowired
    ContractRepository contractRepository;


    public void registerCommands() {
        commands.put("CreateContract", InitiateContractCommand.class);
    }

    @Override
    public void setupCommand(ContractCommand<?> command) {
        command.setJobRepository(contractRepository);
    }
}
