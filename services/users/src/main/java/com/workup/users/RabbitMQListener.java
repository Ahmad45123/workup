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

  @RabbitHandler
  public ClientSetPhotoResponse receive(ClientSetPhotoRequest in) throws Exception {
    return ((ClientSetPhotoCommand) commandMap.getCommand("ClientSetPhoto")).Run(in);
  }

  @RabbitHandler
  public ClientGetPhotoResponse receive(ClientGetPhotoRequest in) throws Exception {
    return ((ClientGetPhotoCommand) commandMap.getCommand("ClientGetPhoto")).Run(in);
  }

  @RabbitHandler
  public AddFreelancerAchievementResponse receive(AddFreelancerAchievementRequest in)
      throws Exception {
    return ((AddFreelancerAchievementCommand) commandMap.getCommand("AddFreelancerAchievement"))
        .Run(in);
  }

  @RabbitHandler
  public AddFreelancerEducationResponse receive(AddFreelancerEducationRequest in) throws Exception {
    return ((AddFreelancerEducationCommand) commandMap.getCommand("AddFreelancerEducation"))
        .Run(in);
  }

  @RabbitHandler
  public AddFreelancerExperienceResponse receive(AddFreelancerExperienceRequest in)
      throws Exception {
    return ((AddFreelancerExperienceCommand) commandMap.getCommand("AddFreelancerExperience"))
        .Run(in);
  }

  @RabbitHandler
  public AddFreelancerSkillResponse receive(AddFreelancerSkillRequest in) throws Exception {
    return ((AddFreelancerSkillCommand) commandMap.getCommand("AddFreelancerSkill")).Run(in);
  }

  @RabbitHandler
  public AddFreelancerLanguageResponse receive(AddFreelancerLanguageRequest in) throws Exception {
    return ((AddFreelancerLanguageCommand) commandMap.getCommand("AddFreelancerLanguage")).Run(in);
  }

  @RabbitHandler
  public GetFreelancerAchievementsResponse receive(GetFreelancerAchievementsRequest in)
      throws Exception {
    return ((GetFreelancerAchievementsCommand) commandMap.getCommand("GetFreelancerAchievements"))
        .Run(in);
  }

  @RabbitHandler
  public GetFreelancerEducationsResponse receive(GetFreelancerEducationsRequest in)
      throws Exception {
    return ((GetFreelancerEducationsCommand) commandMap.getCommand("GetFreelancerEducations"))
        .Run(in);
  }

  @RabbitHandler
  public GetFreelancerExperiencesResponse receive(GetFreelancerExperiencesRequest in)
      throws Exception {
    return ((GetFreelancerExperiencesCommand) commandMap.getCommand("GetFreelancerExperiences"))
        .Run(in);
  }

  @RabbitHandler
  public GetFreelancerSkillsResponse receive(GetFreelancerSkillsRequest in) throws Exception {
    return ((GetFreelancerSkillsCommand) commandMap.getCommand("GetFreelancerSkills")).Run(in);
  }

  @RabbitHandler
  public GetFreelancerLanguagesResponse receive(GetFreelancerLanguagesRequest in) throws Exception {
    return ((GetFreelancerLanguagesCommand) commandMap.getCommand("GetFreelancerLanguages"))
        .Run(in);
  }

  @RabbitHandler
  public UpdateFreelancerAchievementResponse receive(UpdateFreelancerAchievementRequest in)
      throws Exception {
    return ((UpdateFreelancerAchievementCommand)
            commandMap.getCommand("UpdateFreelancerAchievement"))
        .Run(in);
  }

  @RabbitHandler
  public UpdateFreelancerEducationResponse receive(UpdateFreelancerEducationRequest in)
      throws Exception {
    return ((UpdateFreelancerEducationCommand) commandMap.getCommand("UpdateFreelancerEducation"))
        .Run(in);
  }

  @RabbitHandler
  public UpdateFreelancerExperienceResponse receive(UpdateFreelancerExperienceRequest in)
      throws Exception {
    return ((UpdateFreelancerExperienceCommand) commandMap.getCommand("UpdateFreelancerExperience"))
        .Run(in);
  }

  @RabbitHandler
  public RemoveFreelancerAchievementResponse receive(RemoveFreelancerAchievementRequest in)
      throws Exception {
    return ((RemoveFreelancerAchievementCommand)
            commandMap.getCommand("RemoveFreelancerAchievement"))
        .Run(in);
  }

  @RabbitHandler
  public RemoveFreelancerEducationResponse receive(RemoveFreelancerEducationRequest in)
      throws Exception {
    return ((RemoveFreelancerEducationCommand) commandMap.getCommand("RemoveFreelancerEducation"))
        .Run(in);
  }

  @RabbitHandler
  public RemoveFreelancerExperienceResponse receive(RemoveFreelancerExperienceRequest in)
      throws Exception {
    return ((RemoveFreelancerExperienceCommand) commandMap.getCommand("RemoveFreelancerExperience"))
        .Run(in);
  }

  @RabbitHandler
  public RemoveFreelancerSkillResponse receive(RemoveFreelancerSkillRequest in) throws Exception {
    return ((RemoveFreelancerSkillCommand) commandMap.getCommand("RemoveFreelancerSkill")).Run(in);
  }

  @RabbitHandler
  public RemoveFreelancerLanguageResponse receive(RemoveFreelancerLanguageRequest in)
      throws Exception {
    return ((RemoveFreelancerLanguageCommand) commandMap.getCommand("RemoveFreelancerLanguage"))
        .Run(in);
  }
}
