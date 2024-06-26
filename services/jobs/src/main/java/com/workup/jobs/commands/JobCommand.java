package com.workup.jobs.commands;

import com.workup.jobs.repositories.JobRepository;
import com.workup.jobs.repositories.ProposalRepository;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import lombok.Setter;
import org.springframework.amqp.core.AmqpTemplate;

public abstract class JobCommand<T extends CommandRequest, Q extends CommandResponse>
    implements Command<T, Q> {

  @Setter public JobRepository jobRepository;

  @Setter public ProposalRepository proposalRepository;

  @Setter public AmqpTemplate rabbitTemplate;
}
