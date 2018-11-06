package com.vkbot.strategy;

import com.vkbot.entity.Messages;
import com.vkbot.utils.PhraseUtil;

public abstract class AbstractCommand implements Command {
    private Status status = Status.NEW;
    Messages messages = new Messages();
    PhraseUtil phraseUtil = new PhraseUtil();

    @Override
    public abstract Messages execute(String message, String user);

    @Override
    public void interrupt() {
        status = Status.STOPPED;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public abstract Messages nextPhase(String message, String user);

    @Override
    public void clearPhases() {
        messages.clear();
    }

    Messages completeExecution(){
        status = Status.MANAGED;
        return messages;
    }

    Messages finishExecution(){
        status = Status.STOPPED;
        return messages;
    }

}
