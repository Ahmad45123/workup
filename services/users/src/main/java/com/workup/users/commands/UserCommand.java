package com.workup.users.commands;

import com.workup.shared.commands.Command;
import com.workup.users.repositories.ClientRepository;
import com.workup.users.repositories.ExperienceRepository;
import com.workup.users.repositories.FreelancerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import lombok.Setter;

public abstract class UserCommand<T extends com.workup.shared.commands.CommandRequest, Q extends com.workup.shared.commands.CommandResponse>
        implements Command<T, Q> {

    static final String PHOTO_BUCKET = "photos:";
    static final String RESUME_BUCKET = "resume:";

    @Setter
    FreelancerRepository freelancerRepository;

    @Setter
    ExperienceRepository experienceRepository;

    @Setter
    ClientRepository clientRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;
}