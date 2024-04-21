package com.workup.shared.commands;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public abstract class CommandMap<
  R extends Command<? extends CommandRequest, ? extends CommandResponse>
> {

  protected HashMap<String, Class<? extends R>> commands = new HashMap<String, Class<? extends R>>();

  public CommandMap() {
    registerCommands();
  }

  public abstract void registerCommands();

  public abstract void setupCommand(R command);

  public R getCommand(String command) throws Exception {
    Class<? extends R> commandClass = commands.get(command);
    Constructor<? extends R> constructor = commandClass.getConstructor();
    R commandInstance = constructor.newInstance();
    setupCommand(commandInstance);
    return commandInstance;
  }
}
