package com.vkbot.rest;

import com.vkbot.bot.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.stream.Collectors;

@Provider
@Path("/callback")
public class CallbackDispatcher extends Application {

    @Inject
    private MessageHandler callbackApiHandler;

    private Logger logger = LoggerFactory.getLogger(App.class);

    @GET
    public String get(@Context HttpServletRequest request) {
        return "ready";
    }

    @POST
    public String post(@Context HttpServletRequest request) throws IOException {
        logger.info("got a message" );
        request.setCharacterEncoding("UTF-8");

        String msg = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        logger.info("The msg is " + msg);
        callbackApiHandler.parse(msg);

        return callbackApiHandler.getCallBack();
    }


}
