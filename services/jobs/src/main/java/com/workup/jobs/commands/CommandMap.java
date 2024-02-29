package com.workup.jobs.commands;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;

@Component
public class CommandMap {
    HashMap<String, Class<?>> commands = new HashMap<String, Class<?>>();

    public CommandMap() {
        commands.put("CreateJob", CreateJobCommand.class);
    }

    public <T extends CommandRequest> Command<T> GetCommand(String command) throws Exception {
        Class<?> commandClass = commands.get(command);
        Constructor<?> constructor = commandClass.getConstructor();
        Command<T> commandInstance = (Command<T>) constructor.newInstance();
        return commandInstance;
    }
}
