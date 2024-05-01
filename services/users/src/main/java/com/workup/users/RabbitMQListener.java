package com.workup.users;

import com.workup.shared.commands.users.requests.*;
import com.workup.shared.commands.users.responses.*;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.users.commands.*;
import com.workup.users.commands.UserCommandMap;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.USERS)
public class RabbitMQListener {

  @Autowired public UserCommandMap commandMap;

  @RabbitHandler
  public FreelancerGetProfileBriefResponse receive(FreelancerGetProfileBriefRequest in)
      throws Exception {
    return ((FreelancerGetProfileBriefCommand) commandMap.getCommand("FreelancerGetProfileBrief"))
        .Run(in);
  }

  @RabbitHandler
  public FreelancerGetProfileResponse receive(FreelancerGetProfileRequest in) throws Exception {
    return ((FreelancerGetProfileCommand) commandMap.getCommand("FreelancerGetProfile")).Run(in);
  }

  @RabbitHandler
  public FreelancerGetResumeResponse receive(FreelancerGetResumeRequest in) throws Exception {
    return ((FreelancerGetResumeCommand) commandMap.getCommand("FreelancerGetResume")).Run(in);
  }

  @RabbitHandler
  public FreelancerSetPhotoResponse receive(FreelancerSetPhotoRequest in) throws Exception {
    return ((FreelancerSetPhotoCommand) commandMap.getCommand("FreelancerSetPhoto")).Run(in);
  }

  @RabbitHandler
  public FreelancerSetProfileResponse receive(FreelancerSetProfileRequest in) throws Exception {
    return ((FreelancerSetProfileCommand) commandMap.getCommand("FreelancerSetProfile")).Run(in);
  }

  @RabbitHandler
  public FreelancerSetResumeResponse receive(FreelancerSetResumeRequest in) throws Exception {
    return ((FreelancerSetResumeCommand) commandMap.getCommand("FreelancerSetResume")).Run(in);
  }

  @RabbitHandler
  public FreelancerGetPhotoResponse receive(FreelancerGetPhotoRequest in) throws Exception {
    return ((FreelancerGetPhotoCommand) commandMap.getCommand("FreelancerGetPhoto")).Run(in);
  }

  @RabbitHandler
  public ClientSetProfileResponse receive(ClientSetProfileRequest in) throws Exception {
    return ((ClientSetProfileCommand) commandMap.getCommand("ClientSetProfile")).Run(in);
  }

  @RabbitHandler
  public ClientGetProfileResponse receive(ClientGetProfileRequest in) throws Exception {
    return ((ClientGetProfileCommand) commandMap.getCommand("ClientGetProfile")).Run(in);
  }
}
