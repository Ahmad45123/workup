package com.workup.jobs.commands;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandMap;
import com.workup.shared.commands.CommandRequest;

@Component
public class JobCommandMap extends CommandMap<JobCommand<?>> {


    public void registerCommands() {
        commands.put("CreateJob", CreateJobCommand.class);
    }

    @Override
    public void setupCommand(JobCommand<?> command) {

    }
}
