package com.workup.contracts.commands;

import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.contracts.repositories.ContractRepository;
import com.workup.contracts.repositories.TerminationRequestRepository;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.redis.RedisService;
import lombok.Setter;
import org.springframework.amqp.core.AmqpTemplate;

public abstract class ContractCommand<T extends CommandRequest, Q extends CommandResponse>
    implements Command<T, Q> {

  @Setter public AmqpTemplate rabbitTemplate;

  @Setter public ContractRepository contractRepository;

  @Setter public ContractMilestoneRepository contractMilestoneRepository;

  @Setter public TerminationRequestRepository terminationRequestRepository;

  @Setter public RedisService redisService;
}
