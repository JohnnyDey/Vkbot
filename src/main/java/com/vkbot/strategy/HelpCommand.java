package com.vkbot.strategy;


import com.vkbot.entity.Messages;

public class HelpCommand extends AbstractCommand {
    @Override
    public Messages execute(String message, String user) {
        messages.addPhrases(phraseUtil.getHelp());
        return finishExecution();
    }

    @Override
    public Messages nextPhase(String message, String user) {
        return null;
    }
}
