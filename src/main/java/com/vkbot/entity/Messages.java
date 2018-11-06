package com.vkbot.entity;

import com.vkbot.keyboard.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Messages {

    private List<Object> phrases = new ArrayList<>();
    private Keyboard keyboard = null;

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
        return keyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }
}
