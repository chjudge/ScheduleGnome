package ScheduleGnome;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class DBOperator {
    private static Connection conn;

    public DBOperator(String username, String password, String schema)
        throws SQLException
    {
        // Fill in code here to initialize conn so it connects to the database
        // using the provided parameters
        conn = null;

        //Get a properties variable so we can pass the username and password to
        // the database.
        Properties info = new Properties();
        info.put("user", username);
        info.put("password", password);
        //connect to the database
        conn = DriverManager.getConnection("jdbc:mysql://GNOMEDBS/" + schema, info);

    }

    public boolean insertCourse() {
        return true;
    }


    public boolean insertUser() {
        return true;
    }

}
