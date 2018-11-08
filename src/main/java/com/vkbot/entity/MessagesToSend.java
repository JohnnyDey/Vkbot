package com.vkbot.entity;

import com.vkbot.keyboard.Keyboard;
import com.vkbot.keyboard.KeyboardMatcher;
import com.vkbot.utils.KeyboardMap;

import java.util.ArrayList;
import java.util.List;

public class MessagesToSend {

    private List<Object> phrases = new ArrayList<>();
    private KeyboardMap keyboard = null;

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
        return KeyboardMatcher.match(keyboard);
    }

    public void setKeyboard(KeyboardMap keyboard) {
        this.keyboard = keyboard;
    }
}
