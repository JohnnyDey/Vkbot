package com.vkbot.strategy;

import com.vkbot.entity.Messages;
import com.vkbot.services.TimersService;

import javax.ejb.Timer;
import javax.inject.Inject;
import java.util.List;

public class MyReminds extends AbstractCommand {

    private List<Timer> timers;

    @Inject
    private TimersService timersService;

    @Override
    public Messages execute(String message, String user) {
        return refreshTimers(Long.valueOf(user));
    }

    private Messages refreshTimers(Long id){
        timers = timersService.findTimers(id);
        if (timers.size() == 0){
            messages = phraseUtil.noTimers();
            return finishExecution();
        }else {
            messages =  phraseUtil.timersList(timers);
            return completeExecution();
        }
    }

    @Override
    public Messages nextPhase(String message, String user) {
        int index = Integer.parseInt(message) - 1;
        if(timers.size() > index && index >= 0){
            timers.get(index).cancel();
            return refreshTimers(Long.valueOf(user)); //ID а не NAME!
        } else {
            messages = phraseUtil.toBigInteger();
        }
        return completeExecution();
    }


}
