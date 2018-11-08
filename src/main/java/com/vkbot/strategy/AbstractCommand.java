package com.vkbot.strategy;

import com.vk.api.sdk.objects.messages.Message;
import com.vkbot.entity.MessagesToSend;
import com.vkbot.utils.PhraseUtil;

import javax.inject.Inject;

public abstract class AbstractCommand implements Command {
    private Status status = Status.NEW;
    MessagesToSend messagesToSend = new MessagesToSend();
    PhraseUtil phraseUtil = new PhraseUtil();

    @Inject
    Message message;

    @Override
    public abstract MessagesToSend execute(String user);

    @Override
    public void interrupt() {
        status = Status.STOPPED;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public abstract MessagesToSend nextPhase(String user);

    @Override
    public void clearPhases() {
        messagesToSend.clear();
    }

    MessagesToSend completeExecution(){
        status = Status.MANAGED;
        return messagesToSend;
    }

    MessagesToSend finishExecution(){
        status = Status.STOPPED;
        return messagesToSend;
    }

}
