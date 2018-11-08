package com.vkbot.bot;

import com.vk.api.sdk.objects.messages.Message;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

@RequestScoped
public class MessageHolder {

    private Message message;

    public void setMessage(Message message) {
        this.message = message;
    }

    @Produces
    public Message getMessage() {
        return message;
    }
}
