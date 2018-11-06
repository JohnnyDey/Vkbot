package com.vkbot.bot;

import com.google.gson.Gson;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import com.vkbot.entity.Messages;
import com.vkbot.keyboard.Keyboard;
import com.vkbot.rest.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;

public class MessageBuilder {

    private Logger logger = LoggerFactory.getLogger(App.class);

    @Inject
    @Named("api")
    private VkApiClient apiClient;

    @Inject @Named("actor")
    private GroupActor groupActor;

    private Integer id;
    private Messages messages;
    private MessagesSendQuery query;
    private static final String KEYBOARD_PARAM = "keyboard";

    private MessageBuilder() {
        MessagesSendQuery messages = apiClient.messages().send(groupActor);
    }

    static MessageBuilder getInstance(){
        return new MessageBuilder();
    }

    public MessageBuilder to(Integer id){
        query.userId(id);
        return this;
    }

    public MessageBuilder message(Messages message){
        this.messages = message;
        return this;
    }

    public MessageBuilder keyboard(Keyboard keyboard){
        query.unsafeParam("keyboard", new Gson().toJson(keyboard));
        return this;
    }

    void send(){
        if (messages == null) return;
        Iterator<Object> iterator = messages.getPhrases().iterator();
        while (iterator.hasNext()){
            Object msg = iterator.next();

            if(msg instanceof String){
                query.message((String) msg);
            } else if(msg instanceof Integer){
                query.stickerId((Integer) msg);
            } else return;

            if(!iterator.hasNext() && messages.getKeyboard() != null){
                query.unsafeParam(KEYBOARD_PARAM, getKeyboardJson());
            }
           sendMessage();
        }
    }

    private String getKeyboardJson(){
        return new Gson().toJson(messages.getKeyboard());
    }

    private void sendMessage(){
        try {
            query.execute();
        } catch (ApiException | ClientException e) {
            logger.error("Exception while sending msg: ", e.getMessage());
        }
    }
}
