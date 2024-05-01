package com.workup.users;

import com.workup.shared.commands.users.requests.FreelancerGetProfileBriefRequest;
import com.workup.shared.commands.users.requests.FreelancerGetProfileRequest;
import com.workup.shared.commands.users.requests.FreelancerRegisterRequest;
import com.workup.shared.commands.users.responses.FreelancerGetProfileBriefResponse;
import com.workup.shared.commands.users.responses.FreelancerGetProfileResponse;
import com.workup.shared.commands.users.responses.FreelancerRegisterResponse;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.users.commands.FreelancerGetProfileBriefCommand;
import com.workup.users.commands.FreelancerGetProfileCommand;
import com.workup.users.commands.UserCommandMap;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.USERS)
public class RabbitMQListener {

  @Autowired public UserCommandMap commandMap;

  // @RabbitHandler
  // public CreatePaymentRequestResponse receive(CreatePaymentRequestRequest in) throws Exception {
  //   return ((CreatePaymentRequestCommand) commandMap.getCommand("CreatePaymentRequest")).Run(in);
  // }
  // create one for FreelancerGetProfileBriefCommand

  @RabbitHandler
  public FreelancerGetProfileBriefResponse receive(FreelancerGetProfileBriefRequest in) throws Exception {
    return ((FreelancerGetProfileBriefCommand) commandMap.getCommand("FreelancerGetProfileBrief")).Run(in);
  }
}