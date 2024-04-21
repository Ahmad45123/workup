package com.workup.users.commands;

import com.workup.shared.commands.Command;
import com.workup.users.repositories.ClientRepository;
import com.workup.users.repositories.ExperienceRepository;
import com.workup.users.repositories.FreelancerRepository;

import lombok.Setter;

public abstract class UserCommand<T extends com.workup.shared.commands.CommandRequest, Q extends com.workup.shared.commands.CommandResponse> implements Command<T, Q> {

    @Setter
    FreelancerRepository freelancerRepository;

    @Setter
    ExperienceRepository experienceRepository;

    @Setter
    ClientRepository clientRepository;
}