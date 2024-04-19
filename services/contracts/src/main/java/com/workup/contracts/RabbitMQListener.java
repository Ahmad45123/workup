package com.workup.contracts;

import com.workup.contracts.commands.ContractCommandMap;
import com.workup.contracts.commands.InitiateContractCommand;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.commands.jobs.responses.CreateJobResponse;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "contractsqueue")
public class RabbitMQListener {

    @Autowired
    public ContractCommandMap commandMap;

    @RabbitHandler
    public InitiateContractResponse receive(InitiateContractRequest in) throws Exception {
        InitiateContractResponse response = ((InitiateContractCommand) commandMap.getCommand("InitiateContract")).Run(in);
        return response;
    }

    // NEW_COMMAND_BOILERPLATE

}
