package com.revature.Controller;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.FailedLoginException;

public class ExceptionController implements Controller{

    public static Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    private ExceptionHandler<FailedLoginException> loginFail = (e, ctx) ->{
        logger.warn("The username or password attempted were invalid. Exception message: " + e.getMessage());
        ctx.status(400); //code 404 for not found resources
        ctx.json(e.getMessage());
    };



    @Override
    public void mapEndPoints(Javalin app) {

        app.exception(FailedLoginException.class, loginFail);
    }
}
