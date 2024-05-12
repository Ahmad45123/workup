package com.workup.users;

import com.workup.shared.commands.users.requests.*;
import com.workup.shared.commands.users.responses.*;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.users.commands.*;
import com.workup.users.commands.UserCommandMap;

import java.util.concurrent.CompletableFuture;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.USERS)
public class RabbitMQListener {

  @Autowired public UserCommandMap commandMap;

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerGetProfileBriefResponse> receive(FreelancerGetProfileBriefRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((FreelancerGetProfileBriefCommand) commandMap.getCommand("FreelancerGetProfileBrief"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerGetProfileResponse> receive(FreelancerGetProfileRequest in) throws Exception {
    return CompletableFuture.completedFuture(((FreelancerGetProfileCommand) commandMap.getCommand("FreelancerGetProfile")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerGetResumeResponse> receive(FreelancerGetResumeRequest in) throws Exception {
    return CompletableFuture.completedFuture(((FreelancerGetResumeCommand) commandMap.getCommand("FreelancerGetResume")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerSetPhotoResponse> receive(FreelancerSetPhotoRequest in) throws Exception {
    return CompletableFuture.completedFuture(((FreelancerSetPhotoCommand) commandMap.getCommand("FreelancerSetPhoto")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerSetProfileResponse> receive(FreelancerSetProfileRequest in) throws Exception {
    return CompletableFuture.completedFuture(((FreelancerSetProfileCommand) commandMap.getCommand("FreelancerSetProfile")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerSetResumeResponse> receive(FreelancerSetResumeRequest in) throws Exception {
    return CompletableFuture.completedFuture(((FreelancerSetResumeCommand) commandMap.getCommand("FreelancerSetResume")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerGetPhotoResponse> receive(FreelancerGetPhotoRequest in) throws Exception {
    return CompletableFuture.completedFuture(((FreelancerGetPhotoCommand) commandMap.getCommand("FreelancerGetPhoto")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ClientSetProfileResponse> receive(ClientSetProfileRequest in) throws Exception {
    return CompletableFuture.completedFuture(((ClientSetProfileCommand) commandMap.getCommand("ClientSetProfile")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ClientGetProfileResponse> receive(ClientGetProfileRequest in) throws Exception {
    return CompletableFuture.completedFuture(((ClientGetProfileCommand) commandMap.getCommand("ClientGetProfile")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ClientSetPhotoResponse> receive(ClientSetPhotoRequest in) throws Exception {
    return CompletableFuture.completedFuture(((ClientSetPhotoCommand) commandMap.getCommand("ClientSetPhoto")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ClientGetPhotoResponse> receive(ClientGetPhotoRequest in) throws Exception {
    return CompletableFuture.completedFuture(((ClientGetPhotoCommand) commandMap.getCommand("ClientGetPhoto")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerAchievementResponse> receive(AddFreelancerAchievementRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((AddFreelancerAchievementCommand) commandMap.getCommand("AddFreelancerAchievement"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerEducationResponse> receive(AddFreelancerEducationRequest in) throws Exception {
    return CompletableFuture.completedFuture(((AddFreelancerEducationCommand) commandMap.getCommand("AddFreelancerEducation"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerExperienceResponse> receive(AddFreelancerExperienceRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((AddFreelancerExperienceCommand) commandMap.getCommand("AddFreelancerExperience"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerSkillResponse> receive(AddFreelancerSkillRequest in) throws Exception {
    return CompletableFuture.completedFuture(((AddFreelancerSkillCommand) commandMap.getCommand("AddFreelancerSkill")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerLanguageResponse> receive(AddFreelancerLanguageRequest in) throws Exception {
    return CompletableFuture.completedFuture(((AddFreelancerLanguageCommand) commandMap.getCommand("AddFreelancerLanguage")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerAchievementsResponse> receive(GetFreelancerAchievementsRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((GetFreelancerAchievementsCommand) commandMap.getCommand("GetFreelancerAchievements"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerEducationsResponse> receive(GetFreelancerEducationsRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((GetFreelancerEducationsCommand) commandMap.getCommand("GetFreelancerEducations"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerExperiencesResponse> receive(GetFreelancerExperiencesRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((GetFreelancerExperiencesCommand) commandMap.getCommand("GetFreelancerExperiences"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerSkillsResponse> receive(GetFreelancerSkillsRequest in) throws Exception {
    return CompletableFuture.completedFuture(((GetFreelancerSkillsCommand) commandMap.getCommand("GetFreelancerSkills")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerLanguagesResponse> receive(GetFreelancerLanguagesRequest in) throws Exception {
    return CompletableFuture.completedFuture(((GetFreelancerLanguagesCommand) commandMap.getCommand("GetFreelancerLanguages"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<UpdateFreelancerAchievementResponse> receive(UpdateFreelancerAchievementRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((UpdateFreelancerAchievementCommand)
            commandMap.getCommand("UpdateFreelancerAchievement"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<UpdateFreelancerEducationResponse> receive(UpdateFreelancerEducationRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((UpdateFreelancerEducationCommand) commandMap.getCommand("UpdateFreelancerEducation"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<UpdateFreelancerExperienceResponse> receive(UpdateFreelancerExperienceRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((UpdateFreelancerExperienceCommand) commandMap.getCommand("UpdateFreelancerExperience"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerAchievementResponse> receive(RemoveFreelancerAchievementRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((RemoveFreelancerAchievementCommand)
            commandMap.getCommand("RemoveFreelancerAchievement"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerEducationResponse> receive(RemoveFreelancerEducationRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((RemoveFreelancerEducationCommand) commandMap.getCommand("RemoveFreelancerEducation"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerExperienceResponse> receive(RemoveFreelancerExperienceRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((RemoveFreelancerExperienceCommand) commandMap.getCommand("RemoveFreelancerExperience"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerSkillResponse> receive(RemoveFreelancerSkillRequest in) throws Exception {
    return CompletableFuture.completedFuture(((RemoveFreelancerSkillCommand) commandMap.getCommand("RemoveFreelancerSkill")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerLanguageResponse> receive(RemoveFreelancerLanguageRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((RemoveFreelancerLanguageCommand) commandMap.getCommand("RemoveFreelancerLanguage"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<SignUpAndInResponse> receive(LoginRequest in) throws Exception {
    return CompletableFuture.completedFuture(((LoginCommand) commandMap.getCommand("Login")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<SignUpAndInResponse> receive(FreelancerRegisterRequest in) throws Exception {
    return CompletableFuture.completedFuture(((FreelancerRegisterCommand) commandMap.getCommand("FreelancerRegister")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<SignUpAndInResponse> receive(ClientRegisterRequest in) throws Exception {
    return CompletableFuture.completedFuture(((ClientRegisterCommand) commandMap.getCommand("ClientRegister")).Run(in));
  }
}
