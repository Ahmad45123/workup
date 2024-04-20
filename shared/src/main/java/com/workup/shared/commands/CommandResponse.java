package com.workup.shared.commands;

import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
public abstract class CommandResponse {
    public boolean success;
}
