package com.vkbot.utils;


import com.vkbot.entity.Messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PhraseUtil implements Serializable {

    private final Properties properties = new Properties();

    public PhraseUtil(){
        try {
            InputStream resourceAsStream = PhraseUtil.class.getClassLoader().getResourceAsStream("core/phrases.properties");
            properties.load(new InputStreamReader(resourceAsStream));
        }catch (IOException e){

        }
    }

    public Messages getTimerTime() {
        Messages messages = new Messages();
        messages.addPhrase(KeyboardMap.SET_TIME);
        messages.addPhrase(properties.getProperty("timer.get-time"));
        return messages;
    }

    public Messages getTimerMsg() {
        Messages messages = new Messages();

        messages.addPhrase(KeyboardMap.CANCEL);
        messages.addPhrase(properties.getProperty("timer.get-msg"));
        return messages;
    }

    public Messages getSuccessTimedPhrase() {
        Messages messages = new Messages();

        messages.addPhrase(KeyboardMap.LIST);
        messages.addPhrase(properties.getProperty("timer.ok"));
        messages.addPhrase(StickerCollector.thumbUp);
        return messages;
    }
//
//    public Messages getInfoPhrase(User user) {
//        Messages messages = new Messages();

//        StringBuilder sb = new StringBuilder();
//        if(user.getUserName() != null) {
//            sb.append(String.format(properties.getProperty("info.name"), user.getUserName())).append("\n");
//        }
//        messages.addPhrase(sb.toString());
//        messages.addPhrase(KeyboardMap.LIST);
//        messages.addPhrase(StickerCollector.glad);
//        return messages;
//    }

    public Messages choosePerson() {
        Messages messages = new Messages();

        messages.addPhrase(KeyboardMap.PERSONS);
        messages.addPhrase(properties.getProperty("info.choose"));
        return messages;
    }

    public Messages personInfo(String name, int i) {
        Messages messages = new Messages();

        messages.addPhrase(KeyboardMap.NEXT);
        messages.addPhrase(properties.getProperty("info." + name + "." + i));
        return messages;
    }

    public Messages getHelp() {
        Messages messages = new Messages();

        messages.addPhrase(properties.getProperty("help.list"));
        messages.addPhrase(KeyboardMap.LIST);
        messages.addPhrase(StickerCollector.relax);
        return messages;
    }

    public Messages notifyNo(){
        Messages messages = new Messages();

        messages.addPhrase(properties.getProperty("notify.no"));
        messages.addPhrase(KeyboardMap.LIST);
        messages.addPhrase(StickerCollector.sad);
        return messages;
    }

    public Messages notifyYes(){
        Messages messages = new Messages();

        messages.addPhrase(properties.getProperty("notify.yes"));
        messages.addPhrase(KeyboardMap.LIST);
        messages.addPhrase(StickerCollector.thumbUp);
        return messages;
    }

    public String howToNotify() {
        return properties.getProperty("notify.disclaimer");
    }

    public Messages getNotifyHelp(String onOrOffString) {
        Messages messages = new Messages();

        messages.addPhrase(KeyboardMap.YES_OR_NO);
        messages.addPhrase(String.format(properties.getProperty("notify.help"), onOrOffString));
        return messages;
    }

    public Messages askToStartNotify() {
        Messages messages = new Messages();

        messages.addPhrase(KeyboardMap.YES_OR_NO);
        messages.addPhrase(properties.getProperty("notify.ask.start"));
        return messages;
    }

    public Messages askToStopNotify() {
        Messages messages = new Messages();

        messages.addPhrase(KeyboardMap.YES_OR_NO);
        messages.addPhrase(properties.getProperty("notify.ask.stop"));
        return messages;
    }

    public Messages notifyNoChanges() {
        Messages messages = new Messages();

        messages.addPhrase(properties.getProperty("notify.no-changes"));
        messages.addPhrase(KeyboardMap.LIST);
        messages.addPhrase(StickerCollector.pleased);
        return messages;
    }

    public Messages ok(){
        Messages messages = new Messages();

        messages.addPhrase(properties.getProperty("common.ok"));
        messages.addPhrase(KeyboardMap.LIST);
        messages.addPhrase(StickerCollector.thumbUp);
        return messages;
    }

    public Messages toBigInteger(){
        Messages messages = new Messages();

        messages.addPhrase(KeyboardMap.CANCEL);
        messages.addPhrase(properties.getProperty("timers.out-of-bounds"));

        return messages;
    }

    public Messages noTimers(){
        Messages messages = new Messages();

        messages.addPhrase(KeyboardMap.LIST);
        messages.addPhrase(properties.getProperty("timers.list.empty"));
        return messages;
    }

//    public Messages timersList(List<Timer> timers){
//        Messages messages = new Messages();

//        messages.addPhrase(KeyboardMap.CANCEL);
//        StringBuilder sb = new StringBuilder(properties.getProperty("timers.list"));
//        for(int i = 1; i <= timers.size(); i++){
//            sb.append(formatDate(i, timers.get(i - 1)));
//        }
//        messages.addPhrase(sb.toString());
//        return messages;
//    }
//
//    private String formatDate(int index, Timer t){
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(t.getNextTimeout());
//        return "\n" + index + ". " + calendar.get(Calendar.DAY_OF_MONTH) + "-" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) +
//                " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) +
//                " Текст: " + ((TimerId) t.getInfo()).getMsg();
//    }
}
