package com.vkbot.strategy;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class CommandFactory {

    public static final String INFO = "Информация";
    public static final String REMIND = "Напомнить";
    public static final String MY_REMINDS = "Мои напоминалки";
    public static final String CANCEL = "Хватит";
    public static final String NEXT = "Дальше";

    @Inject
    private Instance<Command> commandInstance;

    private String message;

    private String user;

    private final Map<String, Command> sessions = new ConcurrentHashMap<>();

    @Produces
    @Named(value = "execCommand")
    public Command getCommand(){
        Command command = sessions.computeIfAbsent(user, s -> getNewCommand());
        if(command.getStatus().equals(Command.Status.STOPPED)){
            command = getNewCommand();
            sessions.put(user, command);
        }
        return command;
    }

    private Command getNewCommand(){
        switch (message) {
            case REMIND:
                return commandInstance.select(RemindCommand.class).get();
            case INFO:
                return commandInstance.select(InfoCommand.class).get();
            case MY_REMINDS:
                return commandInstance.select(MyRemindsCommand.class).get();
            default:
                return commandInstance.select(HelpCommand.class).get();
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
