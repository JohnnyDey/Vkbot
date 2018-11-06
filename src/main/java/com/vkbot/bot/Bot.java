package com.vkbot.bot;

import com.vkbot.entity.Messages;
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

    public Messages simpleTextMessageHandle(String username, String message) {
        logger.debug("User " + username + "send message: " + message);
        setContextForCommand(message, username);

        Command command = commandInstance.get();
        if(command.getStatus().equals(AbstractCommand.Status.NEW)){
            return command.execute(message.trim(), username);
        }else {
            if(message.startsWith(CommandFactory.CANCEL)){
                command.interrupt();
                return commandInstance.get().execute(message, username);
            } else {
                command.clearPhases();
                return command.nextPhase(message.trim(), username);
            }
        }
    }

    private void setContextForCommand(String message, String user){
        commandContext.setUser(user);
        commandContext.setMessage(message);
    }
}
