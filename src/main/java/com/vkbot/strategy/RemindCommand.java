package com.vkbot.strategy;

import com.vk.api.sdk.objects.messages.Message;
import com.vkbot.entity.MessagesToSend;
import com.vkbot.services.TimersService;

import javax.inject.Inject;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RemindCommand extends AbstractCommand {

    private ZonedDateTime zonedDateTime;

    @Inject
    private TimersService timersService;

    @Override
    public MessagesToSend execute(String  user) {
        messagesToSend = phraseUtil.getTimerTime();
        return completeExecution();
    }

    @Override
    public MessagesToSend nextPhase(String user) {
        Message message = messageInstance.get();
        if(zonedDateTime == null){
            zonedDateTime = parseDate(message.getBody());
            messagesToSend = phraseUtil.getTimerMsg();
        } else {
            startTimer();
            messagesToSend = phraseUtil.getSuccessTimedPhrase();
            return finishExecution();
        }
        return completeExecution();
    }

    private ZonedDateTime parseDate(String message) {

        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        switch (message){
            case "5 минут":
                return now.plusMinutes(5);
            case "10 минут":
                return now.plusMinutes(10);
            case "30 минут":
                return now.plusMinutes(30);
            case "Час":
                return now.plusHours(1);
            case "Два час":
                return now.plusHours(2);
            case "6 часов":
                return now.plusHours(1);
            case "12 часов":
                return now.plusHours(12);
            case "Сутки":
                return now.plusDays(1);
            case "Два дня":
                return now.plusDays(2);
            case "Неделю":
                return now.plusDays(7);
            case "Две недели":
                return now.plusDays(14);
            case "Месяц":
                return now.plusDays(Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH));
            default:
                return now;
        }
    }

    private void startTimer(){
        Message message = messageInstance.get();
        timersService.startTimer(Date.from(zonedDateTime.toInstant()), message.getUserId(), message.getBody());
    }

}
