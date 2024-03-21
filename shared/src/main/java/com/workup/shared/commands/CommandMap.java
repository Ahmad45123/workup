package com.workup.shared.commands;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public abstract class CommandMap<R extends Command>{
    protected HashMap<String, Class<?>> commands = new HashMap<String, Class<?>>();

    public CommandMap() {
        registerCommands();
    }

    public abstract void registerCommands();
    public abstract void setupCommand(R command);  

    public R getCommand(String command) throws Exception {
        Class<?> commandClass = commands.get(command);
        Constructor<?> constructor = commandClass.getConstructor();
        R commandInstance = (R) constructor.newInstance();
        setupCommand(commandInstance);
        return commandInstance;
    }
}
