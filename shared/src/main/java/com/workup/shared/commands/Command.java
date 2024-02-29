package com.workup.shared.commands;

public interface Command<T extends CommandRequest> {
    void Run(T request);
}