package ScheduleGnome;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBPRACTICE {
    private static final String USERNAME = "schedulegnome";
    private static final String PASSWORD = "gnomepassword";
    private static final String CONNECTION_STRING = "jdbc:mysql://108.52.164.126:3306/gnomedb";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("user", USERNAME);
        props.put("password", PASSWORD);
        try {
            Connection conn = DriverManager.getConnection(CONNECTION_STRING, props);
            System.out.println("Connected to the database");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
