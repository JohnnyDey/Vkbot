package com.vkbot.strategy;

import com.vkbot.entity.Messages;

import javax.ejb.Timer;
import java.util.List;

public class MyReminds extends AbstractCommand {

    private List<Timer> timers;

    @Override
    public Messages execute(String message, String user) {
        messages = phraseUtil.unavailable();
        return finishExecution();
    }


    @Override
    public Messages nextPhase(String message, String user) {
        return completeExecution();
    }


}
