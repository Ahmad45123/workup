package com.workup.users;

import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
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
@RabbitListener(queues = ServiceQueueNames.USERS, id = ServiceQueueNames.USERS)
public class RabbitMQListener {

  @Autowired public UserCommandMap commandMap;

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerGetProfileBriefResponse> receive(
      FreelancerGetProfileBriefRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (FreelancerGetProfileBriefResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerGetProfileBrief"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerGetProfileResponse> receive(FreelancerGetProfileRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (FreelancerGetProfileResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerGetProfile")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerGetResumeResponse> receive(FreelancerGetResumeRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (FreelancerGetResumeResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerGetResume")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerSetPhotoResponse> receive(FreelancerSetPhotoRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (FreelancerSetPhotoResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerSetPhoto")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerSetProfileResponse> receive(FreelancerSetProfileRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (FreelancerSetProfileResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerSetProfile")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerSetResumeResponse> receive(FreelancerSetResumeRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (FreelancerSetResumeResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerSetResume")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerGetPhotoResponse> receive(FreelancerGetPhotoRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (FreelancerGetPhotoResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerGetPhoto")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ClientSetProfileResponse> receive(ClientSetProfileRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (ClientSetProfileResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("ClientSetProfile")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ClientGetProfileResponse> receive(ClientGetProfileRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (ClientGetProfileResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("ClientGetProfile")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ClientSetPhotoResponse> receive(ClientSetPhotoRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (ClientSetPhotoResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("ClientSetPhoto")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ClientGetPhotoResponse> receive(ClientGetPhotoRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (ClientGetPhotoResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("ClientGetPhoto")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerAchievementResponse> receive(
      AddFreelancerAchievementRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (AddFreelancerAchievementResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("AddFreelancerAchievement"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerEducationResponse> receive(AddFreelancerEducationRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (AddFreelancerEducationResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("AddFreelancerEducation")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerExperienceResponse> receive(
      AddFreelancerExperienceRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (AddFreelancerExperienceResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("AddFreelancerExperience"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerSkillResponse> receive(AddFreelancerSkillRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (AddFreelancerSkillResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("AddFreelancerSkill")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerLanguageResponse> receive(AddFreelancerLanguageRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (AddFreelancerLanguageResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("AddFreelancerLanguage")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerAchievementsResponse> receive(
      GetFreelancerAchievementsRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (GetFreelancerAchievementsResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("GetFreelancerAchievements"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerEducationsResponse> receive(
      GetFreelancerEducationsRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (GetFreelancerEducationsResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("GetFreelancerEducations"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerExperiencesResponse> receive(
      GetFreelancerExperiencesRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (GetFreelancerExperiencesResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("GetFreelancerExperiences"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerSkillsResponse> receive(GetFreelancerSkillsRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (GetFreelancerSkillsResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("GetFreelancerSkills")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerLanguagesResponse> receive(GetFreelancerLanguagesRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (GetFreelancerLanguagesResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("GetFreelancerLanguages")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<UpdateFreelancerAchievementResponse> receive(
      UpdateFreelancerAchievementRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (UpdateFreelancerAchievementResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("UpdateFreelancerAchievement"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<UpdateFreelancerEducationResponse> receive(
      UpdateFreelancerEducationRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (UpdateFreelancerEducationResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("UpdateFreelancerEducation"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<UpdateFreelancerExperienceResponse> receive(
      UpdateFreelancerExperienceRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (UpdateFreelancerExperienceResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("UpdateFreelancerExperience"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerAchievementResponse> receive(
      RemoveFreelancerAchievementRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (RemoveFreelancerAchievementResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("RemoveFreelancerAchievement"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerEducationResponse> receive(
      RemoveFreelancerEducationRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (RemoveFreelancerEducationResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("RemoveFreelancerEducation"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerExperienceResponse> receive(
      RemoveFreelancerExperienceRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (RemoveFreelancerExperienceResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("RemoveFreelancerExperience"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerSkillResponse> receive(RemoveFreelancerSkillRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (RemoveFreelancerSkillResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("RemoveFreelancerSkill")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerLanguageResponse> receive(
      RemoveFreelancerLanguageRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (RemoveFreelancerLanguageResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("RemoveFreelancerLanguage"))
                .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<SignUpAndInResponse> receive(LoginRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (SignUpAndInResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("Login")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<SignUpAndInResponse> receive(FreelancerRegisterRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(
        (SignUpAndInResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerRegister")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<SignUpAndInResponse> receive(ClientRegisterRequest in) throws Exception {
    return CompletableFuture.completedFuture(
        (SignUpAndInResponse)
            ((Command<CommandRequest, ?>) commandMap.getCommand("ClientRegister")).Run(in));
  }
}
