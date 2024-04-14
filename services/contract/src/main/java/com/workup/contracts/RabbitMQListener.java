package com.workup.contracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workup.contracts.commands.InitiateContractCommand;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workup.contracts.commands.ContractCommandMap;

@Service
@RabbitListener(queues = "contractsqueue")
public class RabbitMQListener {

    @Autowired
    public ContractCommandMap commandMap;

    @RabbitHandler
    public void receive(InitiateContractRequest in) throws Exception {
        ((InitiateContractCommand) commandMap.getCommand("CreateJob")).Run(in);
    }

    @RabbitHandler
    public void receive(CreateProposalRequest in) {
        System.out.println(" [x] Int Received '" + in.getCoverLetter() + "'");
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(in);
            System.out.println(" [x] Received '" + json + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
