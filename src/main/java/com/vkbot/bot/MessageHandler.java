package com.vkbot.bot;

import com.vk.api.sdk.callback.CallbackApi;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.users.UserField;
import com.vkbot.entity.MessagesToSend;
import com.vkbot.utils.MessageUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class MessageHandler extends CallbackApi {

    @Inject
    private Bot bot;

    @Inject @Named("api")
    private VkApiClient apiClient;

    @Inject @Named("actor")
    private GroupActor groupActor;

    @Inject
    private MessageBuilder builder;

    @Inject
    private MessageHolder holder;

    private String answerToVkServer;

    private static final String OK = "ok";

    @Override
    public void messageNew(Integer groupId, Message message){
        if(MessageUtils.isTextMessage(message)){
            String username = getUsername(String.valueOf(message.getUserId()));
            holder.setMessage(message);
            MessagesToSend messagesToSend = bot.simpleTextMessageHandle(username);
            builder.message(messagesToSend).to(message.getUserId()).send();
        }
        answerToVkServer = OK;
    }

    @Override
    public void confirmation(Integer groupId) {
        answerToVkServer = System.getenv("CALL_BACK_ANSWER");
    }

    public String getCallBack(){
        return answerToVkServer;
    }

    private String getUsername(String ids){
        try {
            return apiClient.users().get(groupActor).userIds(ids).fields(UserField.SCREEN_NAME).execute().get(0).toString();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
