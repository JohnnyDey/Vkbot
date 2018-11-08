package com.vkbot.entity;

import com.vkbot.keyboard.Keyboard;
import com.vkbot.keyboard.KeyboardMatcher;
import com.vkbot.utils.KeyboardMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessagesToSend {

    private List<Object> phrases = new ArrayList<>();
    private KeyboardMap keyboard = null;

    public MessagesToSend(String... strings) {
        phrases.addAll(Arrays.asList(strings));
    }

    public void clear(){
        phrases.clear();
    }

    public List<Object> getPhrases(){
        return phrases;
    }

    public void addPhrase(Object msg){
        phrases.add(msg);
    }

    public void addPhrases(List<Object> msges){
        phrases.addAll(msges);
    }

    public Keyboard getKeyboard() {
        return keyboard != null ? KeyboardMatcher.match(keyboard) : null;
    }

    public void setKeyboard(KeyboardMap keyboard) {
        this.keyboard = keyboard;
    }
}
