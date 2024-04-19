package com.workup.shared.commands;

public interface Command<T extends CommandRequest, Q extends CommandResponse> {
    Q Run(T request);
}