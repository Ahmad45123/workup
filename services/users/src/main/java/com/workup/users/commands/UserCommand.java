package com.workup.users.commands;

import com.workup.shared.commands.Command;
import com.workup.users.repositories.AchievementRepository;
import com.workup.users.repositories.ClientRepository;
import com.workup.users.repositories.EducationRepository;
import com.workup.users.repositories.ExperienceRepository;
import com.workup.users.repositories.FreelancerRepository;
import lombok.Setter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

public abstract class UserCommand<
        T extends com.workup.shared.commands.CommandRequest,
        Q extends com.workup.shared.commands.CommandResponse>
    implements Command<T, Q> {

  static final String PHOTO_BUCKET = "photos:";
  static final String RESUME_BUCKET = "resume:";

  @Setter public FreelancerRepository freelancerRepository;

  @Setter public ExperienceRepository experienceRepository;

  @Setter public EducationRepository educationRepository;

  @Setter public AchievementRepository achievementRepository;

  @Setter public ClientRepository clientRepository;

  @Setter public AmqpTemplate rabbitTemplate;
  @Autowired public GridFsTemplate gridFsTemplate;
}
