package com.vkbot.strategy;

import com.vkbot.entity.Messages;


public interface Command {

    Messages execute(String message, String user);

    void interrupt();

    Status getStatus();

    Messages nextPhase(String message, String user);

    void clearPhases();

    enum Status{
        NEW,
        MANAGED,
        STOPPED
    }
}
