package com.workup.contracts.commands;

import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.contracts.repositories.ContractRepository;
import com.workup.contracts.repositories.TerminationRequestRepository;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import lombok.Setter;

public abstract class ContractCommand<T extends CommandRequest, Q extends CommandResponse>
    implements Command<T, Q> {

  @Setter ContractRepository contractRepository;

  @Setter ContractMilestoneRepository contractMilestoneRepository;

  @Setter TerminationRequestRepository terminationRequestRepository;
}
