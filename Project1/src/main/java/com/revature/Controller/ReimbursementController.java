package com.revature.Controller;

import com.revature.dto.AddReimbursementDto;
import com.revature.dto.ResponseReimbursementDto;
import com.revature.service.JWTService;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ReimbursementController implements Controller{

    public static Logger logger = LoggerFactory.getLogger(ReimbursementController.class);

    private JWTService jwtService;
    private ReimbursementService reimbursementService;

    public ReimbursementController() {
        this.jwtService = new JWTService();
        this.reimbursementService = new ReimbursementService();
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


        List<ResponseReimbursementDto> responseReimbursementDtoList = this.reimbursementService.getAllReimbursements();

        logger.info("All reimbursements retrieve successfully");
        ctx.json(responseReimbursementDtoList);
    };


    private Handler getAllReimbursementByEmployeeId = (ctx) -> {

        String employeeId =  ctx.pathParam("employeeId");

        logger.info("Trying to retrieve all reimbursements for employee: " + employeeId);


        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("employee")){
            logger.warn("A non employee user tried to retrieve all reimbursements of an employee. user: " + token.getBody().getSubject());

            throw new UnauthorizedResponse("you must be an employee to access this information");
        }

        int empID = Integer.parseInt(employeeId);
        if(!token.getBody().get("user_id").equals(empID)){
            logger.warn("The employee: "+ token.getBody().getId() + ". user tried to retrieve all reimbursements for employee : "+ employeeId);

            throw new UnauthorizedResponse("you can only see your reimbursements!!");
        }


        List<ResponseReimbursementDto> responseReimbursementDtoList = this.reimbursementService.getAllReimbursementsByEmployeeID(employeeId);

        logger.info("All reimbursements retrieve successfully");
        ctx.json(responseReimbursementDtoList);
    };

    private Handler addReimbursementToAnEmployee = ctx -> {

        String employeeId =  ctx.pathParam("employeeId");

        logger.info("Trying to add a reimbursement for employee: " + employeeId);


        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("employee")){
            logger.warn("A non employee user tried to retrieve all reimbursements of an employee. user: " + token.getBody().getSubject());

            throw new UnauthorizedResponse("you must be an employee to access this information");
        }

        int empID = Integer.parseInt(employeeId);
        if(!token.getBody().get("user_id").equals(empID)){
            logger.warn("The employee: "+ token.getBody().getId() + ". Tried to add a reimbursement for employee : "+ employeeId);

            throw new UnauthorizedResponse("you can only add reimbursements to yourself!!");
        }

        AddReimbursementDto addDto = ctx.bodyAsClass(AddReimbursementDto.class);
        ResponseReimbursementDto response = reimbursementService.addReimbursementByUserId(empID, addDto);

        ctx.json(response);

    };


    @Override
    public void mapEndPoints(Javalin app) {

        app.get("/reimbursements", getAllReimbursements);
        app.get("/employees/{employeeId}/reimbursements", getAllReimbursementByEmployeeId);
        app.post("employees/{employeeId}/reimbursements", addReimbursementToAnEmployee);


    }
}
