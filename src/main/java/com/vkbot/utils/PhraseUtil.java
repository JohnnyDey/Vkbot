package com.vkbot.utils;


import com.vkbot.entity.MessagesToSend;
import com.vkbot.entity.TimerId;
import com.vkbot.rest.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Timer;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class PhraseUtil implements Serializable {

    private final Properties properties = new Properties();
    private Logger logger = LoggerFactory.getLogger(PhraseUtil.class);


    public PhraseUtil(){
        try {
            InputStream resourceAsStream = PhraseUtil.class.getClassLoader().getResourceAsStream("core/phrases.properties");
            properties.load(new InputStreamReader(resourceAsStream));
        }catch (IOException e){
            logger.error("Cannot read phrases!");
        }
    }

    public MessagesToSend getTimerTime() {
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.setKeyboard(KeyboardMap.SET_TIME);
        messagesToSend.addPhrase(properties.getProperty("timer.get-time"));
        return messagesToSend;
    }

    public MessagesToSend getTimerMsg() {
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.setKeyboard(KeyboardMap.CANCEL);
        messagesToSend.addPhrase(properties.getProperty("timer.get-msg"));
        return messagesToSend;
    }

    public MessagesToSend getSuccessTimedPhrase() {
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.setKeyboard(KeyboardMap.LIST);
        messagesToSend.addPhrase(properties.getProperty("timer.ok"));
        messagesToSend.addPhrase(StickerCollector.thumbUp);
        return messagesToSend;
    }

    public MessagesToSend getInfoPhrase(String user) {
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.addPhrase(String.format(properties.getProperty("info.name"), user) + "\n");
        messagesToSend.setKeyboard(KeyboardMap.LIST);
        messagesToSend.addPhrase(StickerCollector.glad);
        return messagesToSend;
    }

    public MessagesToSend choosePerson() {
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.setKeyboard(KeyboardMap.PERSONS);
        messagesToSend.addPhrase(properties.getProperty("info.choose"));
        return messagesToSend;
    }

    public MessagesToSend personInfo(String name, int i) {
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.setKeyboard(KeyboardMap.NEXT);
        messagesToSend.addPhrase(properties.getProperty("info." + name + "." + i));
        return messagesToSend;
    }

    public MessagesToSend getHelp() {
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.addPhrase(properties.getProperty("help.list"));
        messagesToSend.setKeyboard(KeyboardMap.LIST);
        return messagesToSend;
    }

    public MessagesToSend notifyNo(){
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.addPhrase(properties.getProperty("notify.no"));
        messagesToSend.setKeyboard(KeyboardMap.LIST);
        messagesToSend.addPhrase(StickerCollector.sad);
        return messagesToSend;
    }

    public MessagesToSend notifyYes(){
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.addPhrase(properties.getProperty("notify.yes"));
        messagesToSend.setKeyboard(KeyboardMap.LIST);
        messagesToSend.addPhrase(StickerCollector.thumbUp);
        return messagesToSend;
    }

    public String howToNotify() {
        return properties.getProperty("notify.disclaimer");
    }

    public MessagesToSend getNotifyHelp(String onOrOffString) {
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.setKeyboard(KeyboardMap.YES_OR_NO);
        messagesToSend.addPhrase(String.format(properties.getProperty("notify.help"), onOrOffString));
        return messagesToSend;
    }

    public MessagesToSend askToStartNotify() {
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.setKeyboard(KeyboardMap.YES_OR_NO);
        messagesToSend.addPhrase(properties.getProperty("notify.ask.start"));
        return messagesToSend;
    }

    public MessagesToSend askToStopNotify() {
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.setKeyboard(KeyboardMap.YES_OR_NO);
        messagesToSend.addPhrase(properties.getProperty("notify.ask.stop"));
        return messagesToSend;
    }

    public MessagesToSend notifyNoChanges() {
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.addPhrase(properties.getProperty("notify.no-changes"));
        messagesToSend.setKeyboard(KeyboardMap.LIST);
        messagesToSend.addPhrase(StickerCollector.pleased);
        return messagesToSend;
    }

    public MessagesToSend ok(){
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.addPhrase(properties.getProperty("common.ok"));
        messagesToSend.setKeyboard(KeyboardMap.LIST);
        messagesToSend.addPhrase(StickerCollector.thumbUp);
        return messagesToSend;
    }

    public MessagesToSend unavailable(){
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.addPhrase(properties.getProperty("common.unavailable"));
        messagesToSend.setKeyboard(KeyboardMap.LIST);
        messagesToSend.addPhrase(StickerCollector.sad);
        return messagesToSend;
    }

    public MessagesToSend toBigInteger(){
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.setKeyboard(KeyboardMap.CANCEL);
        messagesToSend.addPhrase(properties.getProperty("timers.out-of-bounds"));

        return messagesToSend;
    }

    public MessagesToSend noTimers(){
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.setKeyboard(KeyboardMap.LIST);
        messagesToSend.addPhrase(properties.getProperty("timers.list.empty"));
        return messagesToSend;
    }

    public MessagesToSend timersList(List<Timer> timers){
        MessagesToSend messagesToSend = new MessagesToSend();
        messagesToSend.setKeyboard(KeyboardMap.CANCEL);
        StringBuilder sb = new StringBuilder(properties.getProperty("timers.list"));
        for(int i = 1; i <= timers.size(); i++){
            sb.append(formatDate(i, timers.get(i - 1)));
        }
        messagesToSend.addPhrase(sb.toString());
        return messagesToSend;
    }

    private String formatDate(int index, Timer t){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(t.getNextTimeout());
        return "\n" + index + ". " + calendar.get(Calendar.DAY_OF_MONTH) + "-" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) +
                " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) +
                " Текст: " + ((TimerId) t.getInfo()).getMsg();
    }
}
