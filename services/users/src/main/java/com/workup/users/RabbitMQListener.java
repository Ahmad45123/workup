package com.workup.users;

import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.users.requests.*;
import com.workup.shared.commands.users.responses.*;
import com.workup.shared.enums.HttpStatusCode;
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
    try {
      return CompletableFuture.completedFuture(
          (FreelancerGetProfileBriefResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerGetProfileBrief"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          FreelancerGetProfileBriefResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerGetProfileResponse> receive(FreelancerGetProfileRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (FreelancerGetProfileResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerGetProfile")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          FreelancerGetProfileResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerGetResumeResponse> receive(FreelancerGetResumeRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (FreelancerGetResumeResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerGetResume")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          FreelancerGetResumeResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerSetPhotoResponse> receive(FreelancerSetPhotoRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (FreelancerSetPhotoResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerSetPhoto")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          FreelancerSetPhotoResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerSetProfileResponse> receive(FreelancerSetProfileRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (FreelancerSetProfileResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerSetProfile")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          FreelancerSetProfileResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerSetResumeResponse> receive(FreelancerSetResumeRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (FreelancerSetResumeResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerSetResume")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          FreelancerSetResumeResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<FreelancerGetPhotoResponse> receive(FreelancerGetPhotoRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (FreelancerGetPhotoResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerGetPhoto")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          FreelancerGetPhotoResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ClientSetProfileResponse> receive(ClientSetProfileRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (ClientSetProfileResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("ClientSetProfile")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          ClientSetProfileResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ClientGetProfileResponse> receive(ClientGetProfileRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (ClientGetProfileResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("ClientGetProfile")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          ClientGetProfileResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ClientSetPhotoResponse> receive(ClientSetPhotoRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (ClientSetPhotoResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("ClientSetPhoto")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          ClientSetPhotoResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<ClientGetPhotoResponse> receive(ClientGetPhotoRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (ClientGetPhotoResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("ClientGetPhoto")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          ClientGetPhotoResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerAchievementResponse> receive(
      AddFreelancerAchievementRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (AddFreelancerAchievementResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("AddFreelancerAchievement"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          AddFreelancerAchievementResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerEducationResponse> receive(AddFreelancerEducationRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (AddFreelancerEducationResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("AddFreelancerEducation"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          AddFreelancerEducationResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerExperienceResponse> receive(
      AddFreelancerExperienceRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (AddFreelancerExperienceResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("AddFreelancerExperience"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          AddFreelancerExperienceResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerSkillResponse> receive(AddFreelancerSkillRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (AddFreelancerSkillResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("AddFreelancerSkill")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          AddFreelancerSkillResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AddFreelancerLanguageResponse> receive(AddFreelancerLanguageRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (AddFreelancerLanguageResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("AddFreelancerLanguage"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          AddFreelancerLanguageResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerAchievementsResponse> receive(
      GetFreelancerAchievementsRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetFreelancerAchievementsResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetFreelancerAchievements"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetFreelancerAchievementsResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerEducationsResponse> receive(
      GetFreelancerEducationsRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetFreelancerEducationsResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetFreelancerEducations"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetFreelancerEducationsResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerExperiencesResponse> receive(
      GetFreelancerExperiencesRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetFreelancerExperiencesResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetFreelancerExperiences"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetFreelancerExperiencesResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerSkillsResponse> receive(GetFreelancerSkillsRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetFreelancerSkillsResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetFreelancerSkills")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetFreelancerSkillsResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerLanguagesResponse> receive(GetFreelancerLanguagesRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetFreelancerLanguagesResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetFreelancerLanguages"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetFreelancerLanguagesResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<UpdateFreelancerAchievementResponse> receive(
      UpdateFreelancerAchievementRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (UpdateFreelancerAchievementResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("UpdateFreelancerAchievement"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          UpdateFreelancerAchievementResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<UpdateFreelancerEducationResponse> receive(
      UpdateFreelancerEducationRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (UpdateFreelancerEducationResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("UpdateFreelancerEducation"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          UpdateFreelancerEducationResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<UpdateFreelancerExperienceResponse> receive(
      UpdateFreelancerExperienceRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (UpdateFreelancerExperienceResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("UpdateFreelancerExperience"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          UpdateFreelancerExperienceResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerAchievementResponse> receive(
      RemoveFreelancerAchievementRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (RemoveFreelancerAchievementResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("RemoveFreelancerAchievement"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          RemoveFreelancerAchievementResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerEducationResponse> receive(
      RemoveFreelancerEducationRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (RemoveFreelancerEducationResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("RemoveFreelancerEducation"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          RemoveFreelancerEducationResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerExperienceResponse> receive(
      RemoveFreelancerExperienceRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (RemoveFreelancerExperienceResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("RemoveFreelancerExperience"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          RemoveFreelancerExperienceResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerSkillResponse> receive(RemoveFreelancerSkillRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (RemoveFreelancerSkillResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("RemoveFreelancerSkill"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          RemoveFreelancerSkillResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<RemoveFreelancerLanguageResponse> receive(
      RemoveFreelancerLanguageRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (RemoveFreelancerLanguageResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("RemoveFreelancerLanguage"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          RemoveFreelancerLanguageResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<SignUpAndInResponse> receive(LoginRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (SignUpAndInResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("Login")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          SignUpAndInResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<SignUpAndInResponse> receive(FreelancerRegisterRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (SignUpAndInResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("FreelancerRegister")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          SignUpAndInResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<SignUpAndInResponse> receive(ClientRegisterRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (SignUpAndInResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("ClientRegister")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          SignUpAndInResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }
}
