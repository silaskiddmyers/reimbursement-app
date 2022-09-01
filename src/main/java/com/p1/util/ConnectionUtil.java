package com.p1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {

    static Logger logger = LogManager.getLogger(ConnectionUtil.class);
    private static final String url = "jdbc:postgresql://" + System.getenv("DB_EP") + "/reimbursement-app";
    private static final String username = System.getenv("DB_UN");
    private static final String password = System.getenv("DB_PW");

    public static Connection getConnection(){

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            logger.error("Error while connecting. Printing stack trace...\n\n", e);
        }

        return conn;
    }
}
