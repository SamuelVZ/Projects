package com.revature.Controller;

import com.revature.service.JWTService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReimbursementController implements Controller{

    public static Logger logger = LoggerFactory.getLogger(ReimbursementController.class);

    private JWTService jwtService;

    public ReimbursementController() {
        this.jwtService = new JWTService();
    }

    //only accessible by managers
    private Handler getAllReimbursements = (ctx) -> {
        logger.info("Trying to retrieve all reimbursements");

        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("manager")){
            logger.warn("A non manager user tried to retrieve all reimbursements. user: " + token.getBody().getSubject());

            throw new UnauthorizedResponse("you must be a manager to access this information");
        }


        logger.info("All reimbursements retrieve successfully");

        ctx.json("successful access to the manager info");
    };


    @Override
    public void mapEndPoints(Javalin app) {

        app.get("/reimbursements", getAllReimbursements);
        //app.post("/reimbursements");
    }
}
