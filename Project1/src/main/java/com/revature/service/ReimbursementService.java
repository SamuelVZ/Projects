package com.revature.service;

import com.revature.Controller.ReimbursementController;
import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDto;
import com.revature.dto.ResponseReimbursementDto;
import com.revature.model.Reimbursement;
import com.revature.utility.ConnectionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.spec.InvalidParameterSpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementService {

    public static Logger logger = LoggerFactory.getLogger(ReimbursementService.class);

    private ReimbursementDao reimbursementDao;
    private ResponseReimbursementDto responseReimbursementDto;

    public ReimbursementService(){
        this.reimbursementDao = new ReimbursementDao();
        this.responseReimbursementDto = new ResponseReimbursementDto();
    }

    public List<ResponseReimbursementDto> getAllReimbursements() throws SQLException {
            logger.info("A manager tried to get all the reimbursements");

            List<Reimbursement> reimbursements = reimbursementDao.getAllReimbursements();


            List<ResponseReimbursementDto> responseReimbursementDtos = new ArrayList<>();

            for(Reimbursement r : reimbursements){
                responseReimbursementDtos.add(new ResponseReimbursementDto(r.getId(), r.getAmount(), r.getDateSubmitted(), r.getDateResolved(), r.getDescription(),
                        r.getEmployee().getUsername(), r.getManager().getUsername(), r.getStatusName(), r.getTypeName()));

            }
            return responseReimbursementDtos;


    }


    public List<ResponseReimbursementDto> getAllReimbursementsByEmployeeID(String employeeId) throws SQLException {
        logger.info("A employee tried to get all their reimbursements");

        try{
            int empId = Integer.parseInt(employeeId);

            List <Reimbursement> reimbursements = reimbursementDao.getAllReimbursementsByUserId(empId);

            List<ResponseReimbursementDto> dtos = new ArrayList<>();

            for(Reimbursement r : reimbursements){

                dtos.add(new ResponseReimbursementDto(r.getId(), r.getAmount(), r.getDateSubmitted(), r.getDateResolved(), r.getDescription(),
                        r.getEmployee().getUsername(), r.getManager().getUsername(), r.getStatusName(), r.getTypeName()));

            }
            return  dtos;

        }catch (NumberFormatException e){
            logger.warn("Service layer - the client id: " + employeeId + " is invalid");
            throw new IllegalArgumentException("the client id provided is invalid: " + employeeId);
        }

    }

    public ResponseReimbursementDto addReimbursementByUserId(int empID, AddReimbursementDto addDto) throws SQLException {


        Reimbursement reimbursement = reimbursementDao.addReimbursement(empID, addDto);

        ResponseReimbursementDto dto = new ResponseReimbursementDto(reimbursement.getId(),reimbursement.getAmount(),
                reimbursement.getDateSubmitted(), reimbursement.getDateResolved(), reimbursement.getDescription(),
                reimbursement.getEmployee().getUsername(), reimbursement.getManager().getUsername(), reimbursement.getStatusName(), reimbursement.getTypeName());



        return  dto;
    }
}
