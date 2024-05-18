package com.workup.contracts.commands;

import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.contracts.repositories.ContractRepository;
import com.workup.contracts.repositories.TerminationRequestRepository;
import com.workup.shared.commands.CommandMap;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.redis.RedisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractCommandMap
    extends CommandMap<ContractCommand<? extends CommandRequest, ? extends CommandResponse>> {

  @Autowired public ContractRepository contractRepository;

  @Autowired public ContractMilestoneRepository contractMilestoneRepository;

  @Autowired public TerminationRequestRepository terminationRequestRepository;

  @Autowired public AmqpTemplate rabbitTemplate;

  @Autowired public RedisService redisService;

  public void registerCommands() {
    commands.put("InitiateContract", InitiateContractCommand.class);
    commands.put("RequestContractTermination", RequestContractTerminationCommand.class);
    commands.put("HandleTerminationRequest", HandleTerminationRequestCommand.class);
    commands.put("MarkMilestoneAsPaid", MarkMilestoneAsPaidCommand.class);
    commands.put("ViewContractMilestones", ViewContractMilestonesCommand.class);
    commands.put("GetContract", GetContractCommand.class);
    commands.put("EvaluateMilestone", EvaluateMilestoneCommand.class);
    commands.put("ProgressMilestone", ProgressMilestoneCommand.class);
    commands.put("GetPendingTerminations", GetPendingTerminationsCommand.class);
    // NEW_COMMAND_BOILERPLATE
  }

  @Override
  public void setupCommand(
      ContractCommand<? extends CommandRequest, ? extends CommandResponse> command) {
    command.setContractRepository(contractRepository);
    command.setContractMilestoneRepository(contractMilestoneRepository);
    command.setTerminationRequestRepository(terminationRequestRepository);
    command.setRabbitTemplate(rabbitTemplate);
    command.setRedisService(redisService);
  }
}
