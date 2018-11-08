package com.vkbot.strategy;

import com.vkbot.entity.MessagesToSend;


public interface Command {

    MessagesToSend execute(String user);

    void interrupt();

    Status getStatus();

    MessagesToSend nextPhase(String user);

    void clearPhases();

    enum Status{
        NEW,
        MANAGED,
        STOPPED
    }
}
