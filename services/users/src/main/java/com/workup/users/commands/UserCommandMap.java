package com.workup.users.commands;

import com.workup.shared.commands.CommandMap;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import com.workup.users.repositories.ClientRepository;
import com.workup.users.repositories.ExperienceRepository;
import com.workup.users.repositories.FreelancerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCommandMap
    extends CommandMap<UserCommand<? extends CommandRequest, ? extends CommandResponse>> {
  @Autowired FreelancerRepository freelancerRepository;
  @Autowired ExperienceRepository experienceRepository;
  @Autowired ClientRepository clientRepository;

  public void registerCommands() {

    commands.put("FreelancerGetResume", FreelancerGetResumeCommand.class);
    commands.put("FreelancerSetResume", FreelancerSetResumeCommand.class);

    commands.put("FreelancerGetProfile", FreelancerGetProfileCommand.class);
    commands.put("FreelancerSetProfile", FreelancerSetProfileCommand.class);

    commands.put("FreelancerGetPhoto", FreelancerGetPhotoCommand.class);
    commands.put("FreelancerSetPhoto", FreelancerSetPhotoCommand.class);

    commands.put("FreelancerGetProfileBrief", FreelancerGetProfileBriefCommand.class);

    commands.put("ClientGetProfile", ClientGetProfileCommand.class);
    commands.put("ClientSetProfile", ClientSetProfileCommand.class);

    // NEW_COMMAND_BOILERPLATE
  }

  @Override
  public void setupCommand(
      UserCommand<? extends CommandRequest, ? extends CommandResponse> command) {
    command.setFreelancerRepository(freelancerRepository);
    command.setExperienceRepository(experienceRepository);
    command.setClientRepository(clientRepository);
  }
}
