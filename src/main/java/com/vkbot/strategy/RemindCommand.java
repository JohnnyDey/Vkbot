package com.vkbot.strategy;

import com.vkbot.entity.Messages;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class RemindCommand extends AbstractCommand {

    private ZonedDateTime zonedDateTime;

    @Override
    public Messages execute(String message, String user) {
        messages = phraseUtil.unavailable();
        return finishExecution();
    }

    @Override
    public Messages nextPhase(String message, String user) {
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

}
