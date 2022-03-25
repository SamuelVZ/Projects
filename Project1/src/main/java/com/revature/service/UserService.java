package com.revature.service;

import com.revature.Controller.ExceptionController;
import com.revature.dao.UserDao;
import com.revature.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.FailedLoginException;
import java.sql.SQLException;

public class UserService {

    public static Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;

    public UserService(){
        this.userDao = new UserDao();
    }

    public UserService(UserDao mockDao){
        this.userDao = mockDao;
    }

    public User login(String username, String password) throws SQLException, FailedLoginException {
        logger.info("User service login attempted with username: " + username);

        User user = this.userDao.getUserByUsernameAndPassword(username, password);

        if(user == null){
            throw new FailedLoginException("Invalid username or password");
        }
        return  user;
    }

}
