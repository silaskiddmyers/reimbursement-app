package com.p1.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.common.hash.Hashing;
import com.p1.dao.UserDao;
import com.p1.models.ReimbType;
import com.p1.models.User;
import com.p1.dao.UserDaoJdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {
    static Logger logger = LogManager.getLogger(UserService.class);
    UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserService() {
        this.userDao = new UserDaoJdbc();
    }

    public boolean validateCredentials(User credentials) {

        User userFromStore = userDao.getUserByUsername(credentials.getUsername());
        if (userFromStore == null) {
            return false;
        }

        String sha256hex = Hashing.sha256().hashString(credentials.getPassword(), StandardCharsets.UTF_8).toString();
        System.out.println(credentials.getPassword());
        if (sha256hex.equals(userFromStore.getPassword())) {
            return true;
        }

        return false;
    }

    public User getUserGivenUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public List<ReimbType> getReimbTypes() {
        return userDao.getReimbTypes();
    }
}
