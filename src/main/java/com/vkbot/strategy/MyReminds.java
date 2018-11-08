package com.vkbot.strategy;

import com.vkbot.entity.MessagesToSend;
import com.vkbot.services.TimersService;

import javax.ejb.Timer;
import javax.inject.Inject;
import java.util.List;

public class MyReminds extends AbstractCommand {

    private List<Timer> timers;

    @Inject
    private TimersService timersService;

    @Override
    public MessagesToSend execute(String user) {
        return refreshTimers(Long.valueOf(user));
    }

    private MessagesToSend refreshTimers(Long id){
        timers = timersService.findTimers(id);
        if (timers.size() == 0){
            messagesToSend = phraseUtil.noTimers();
            return finishExecution();
        }else {
            messagesToSend =  phraseUtil.timersList(timers);
            return completeExecution();
        }
    }

    @Override
    public MessagesToSend nextPhase(String user) {
        int index = Integer.parseInt(message.getBody()) - 1;
        if(timers.size() > index && index >= 0){
            timers.get(index).cancel();
            return refreshTimers(Long.valueOf(message.getUserId()));
        } else {
            messagesToSend = phraseUtil.toBigInteger();
        }
        return completeExecution();
    }


}
