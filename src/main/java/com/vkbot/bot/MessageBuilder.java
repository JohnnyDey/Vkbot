package com.vkbot.bot;

import com.google.gson.Gson;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import com.vkbot.entity.MessagesToSend;
import com.vkbot.rest.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;

@RequestScoped
public class MessageBuilder {

    private Logger logger = LoggerFactory.getLogger(App.class);

    @Inject
    @Named("api")
    private VkApiClient apiClient;

    @Inject @Named("actor")
    private GroupActor groupActor;

    private Integer id;
    private MessagesToSend messagesToSend;
    private MessagesSendQuery query;
    private static final String KEYBOARD_PARAM = "keyboard";

    @PostConstruct
    private void init(){
        query = apiClient.messages().send(groupActor);
    }

    MessageBuilder to(Integer id){
        query.userId(id);
        return this;
    }

    public MessageBuilder message(MessagesToSend message){
        this.messagesToSend = message;
        return this;
    }

    void send(){
        if (messagesToSend == null) return;
        Iterator<Object> iterator = messagesToSend.getPhrases().iterator();
        while (iterator.hasNext()){
            Object msg = iterator.next();

            if(msg instanceof String){
                query.message((String) msg);
            } else if(msg instanceof Integer){
                query.stickerId((Integer) msg);
            } else return;

            if(!iterator.hasNext() && messagesToSend.getKeyboard() != null){
                query.unsafeParam(KEYBOARD_PARAM, getKeyboardJson());
            }
           sendMessage();
        }
    }

    private String getKeyboardJson(){
        return new Gson().toJson(messagesToSend.getKeyboard());
    }

    private void sendMessage(){
        try {
            query.execute();
        } catch (ApiException | ClientException e) {
            logger.error("Exception while sending msg: ", e.getMessage());
        }
    }
}
