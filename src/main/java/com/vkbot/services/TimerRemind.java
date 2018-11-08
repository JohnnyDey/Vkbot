package com.vkbot.services;

import com.vkbot.entity.TimerId;

public class TimerRemind {
    TimerRemind(TimerId id) {
        this.id = id;
    }

    private TimerId id;

    public TimerId getId() {
        return id;
    }

    public void setId(TimerId id) {
        this.id = id;
    }
}
