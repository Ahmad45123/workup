package com.workup.contracts.commands;

import org.springframework.beans.factory.annotation.Autowired;

import com.workup.contracts.repositories.ContractRepository;
import org.springframework.stereotype.Component;

import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandMap;
import com.workup.shared.commands.CommandRequest;

@Component
public class ContractCommandMap extends CommandMap<ContractCommand<?>> {
    @Autowired
    ContractRepository contractRepository;


    public void registerCommands() {
        commands.put("InitiateContract", InitiateContractCommand.class);
    }

    @Override
    public void setupCommand(ContractCommand<?> command) {
        command.setJobRepository(contractRepository);
    }
}
