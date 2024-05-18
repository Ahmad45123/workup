package com.workup.users.commands;

import com.workup.shared.commands.CommandMap;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import com.workup.users.repositories.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCommandMap
    extends CommandMap<UserCommand<? extends CommandRequest, ? extends CommandResponse>> {
  @Autowired public FreelancerRepository freelancerRepository;
  @Autowired public ExperienceRepository experienceRepository;
  @Autowired public ClientRepository clientRepository;
  @Autowired public EducationRepository educationRepository;
  @Autowired public AchievementRepository achievementRepository;
  @Autowired public AmqpTemplate rabbitTemplate;

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

    commands.put("ClientGetPhoto", ClientGetPhotoCommand.class);
    commands.put("ClientSetPhoto", ClientSetPhotoCommand.class);

    commands.put("AddFreelancerAchievement", AddFreelancerAchievementCommand.class);
    commands.put("AddFreelancerEducation", AddFreelancerEducationCommand.class);
    commands.put("AddFreelancerExperience", AddFreelancerExperienceCommand.class);
    commands.put("AddFreelancerSkill", AddFreelancerSkillCommand.class);
    commands.put("AddFreelancerLanguage", AddFreelancerLanguageCommand.class);

    commands.put("GetFreelancerAchievements", GetFreelancerAchievementsCommand.class);
    commands.put("GetFreelancerEducations", GetFreelancerEducationsCommand.class);
    commands.put("GetFreelancerExperiences", GetFreelancerExperiencesCommand.class);
    commands.put("GetFreelancerSkills", GetFreelancerSkillsCommand.class);
    commands.put("GetFreelancerLanguages", GetFreelancerLanguagesCommand.class);

    commands.put("RemoveFreelancerAchievement", RemoveFreelancerAchievementCommand.class);
    commands.put("RemoveFreelancerEducation", RemoveFreelancerEducationCommand.class);
    commands.put("RemoveFreelancerExperience", RemoveFreelancerExperienceCommand.class);
    commands.put("RemoveFreelancerSkill", RemoveFreelancerSkillCommand.class);
    commands.put("RemoveFreelancerLanguage", RemoveFreelancerLanguageCommand.class);

    commands.put("UpdateFreelancerAchievement", UpdateFreelancerAchievementCommand.class);
    commands.put("UpdateFreelancerEducation", UpdateFreelancerEducationCommand.class);
    commands.put("UpdateFreelancerExperience", UpdateFreelancerExperienceCommand.class);

    commands.put("Login", LoginCommand.class);
    commands.put("FreelancerRegister", FreelancerRegisterCommand.class);
    commands.put("ClientRegister", ClientRegisterCommand.class);
    // NEW_COMMAND_BOILERPLATE
  }

  @Override
  public void setupCommand(
      UserCommand<? extends CommandRequest, ? extends CommandResponse> command) {
    command.setFreelancerRepository(freelancerRepository);
    command.setExperienceRepository(experienceRepository);
    command.setClientRepository(clientRepository);
    command.setEducationRepository(educationRepository);
    command.setAchievementRepository(achievementRepository);
    command.setRabbitTemplate(rabbitTemplate);
  }
}
