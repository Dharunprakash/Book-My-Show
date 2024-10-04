package com.bms.bms.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseUtil {


    private static Connection connection;

    // Private constructor to prevent instantiation
    private DataBaseUtil() {
    }


    public static Connection getConnection() {

        if (connection == null) {
            synchronized (DataBaseUtil.class) {
                if (connection == null) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        String Url = "jdbc:mysql://localhost:3306/bms";
                        String User = "root";
                        String Password = "Dharun!20";
                        connection = DriverManager.getConnection(Url, User, Password);
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Error connecting to the database", e);
                    }
                }
            }
        }

        return connection;
    }
}
