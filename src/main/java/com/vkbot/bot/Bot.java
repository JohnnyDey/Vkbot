package com.vkbot.bot;

import com.vk.api.sdk.objects.messages.Message;
import com.vkbot.entity.MessagesToSend;
import com.vkbot.strategy.AbstractCommand;
import com.vkbot.strategy.Command;
import com.vkbot.strategy.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class Bot {

    private final Logger logger = LoggerFactory.getLogger(Bot.class);

    @Inject
    private CommandFactory commandContext;

    @Inject
    @Named(value = "execCommand")
    private Instance<Command> commandInstance;

    @Inject
    private Instance<Message> messageInstance;

    MessagesToSend simpleTextMessageHandle(String username) {
        Message message = messageInstance.get();
        logger.debug("User " + username + "send messageInstance: " + message);
        setContextForCommand(message.getBody(), username);

        Command command = commandInstance.get();
        if(command.getStatus().equals(AbstractCommand.Status.NEW)){
            return command.execute(username);
        }else {
            if(message.getBody().startsWith(CommandFactory.CANCEL)){
                command.interrupt();
                return commandInstance.get().execute(username);
            } else {
                command.clearPhases();
                return command.nextPhase(username);
            }
        }
    }

    private void setContextForCommand(String message, String user){
        commandContext.setUser(user);
        commandContext.setMessage(message);
    }
}
