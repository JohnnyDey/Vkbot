package com.vkbot.strategy;

import com.vkbot.entity.MessagesToSend;
import com.vkbot.services.TimersService;

import javax.inject.Inject;

public class AdminCommand extends AbstractCommand{

    private static String TIMER_LIST = "timers";
    private static String STOP_ALL = "stopTimers";

    @Inject
    private TimersService timersService;

    @Override
    public MessagesToSend execute(String user) {
        if(message.getBody().endsWith(TIMER_LIST)){
            timersService.getTimers().forEach(timer ->
                    messagesToSend = new MessagesToSend(timer.getInfo().toString() + " " + timer.getNextTimeout())
            );
            if(messagesToSend.getPhrases().size() == 0){
                messagesToSend.addPhrase("No timers");
            }
        } else if(message.getBody().endsWith(STOP_ALL)){
            timersService.stopTimers();
            messagesToSend = new MessagesToSend("Stopped All timers");
        }
        return finishExecution();
    }

    @Override
    public MessagesToSend nextPhase(String user) {
        return null;
    }
}
