package com.vkbot.strategy;


import com.vkbot.entity.MessagesToSend;

public class HelpCommand extends AbstractCommand {
    @Override
    public MessagesToSend execute(String user) {
        messagesToSend = phraseUtil.getHelp();
        return finishExecution();
    }

    @Override
    public MessagesToSend nextPhase(String user) {
        return null;
    }
}
