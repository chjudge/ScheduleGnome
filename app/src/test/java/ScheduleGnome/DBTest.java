package ScheduleGnome;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBTest {

    @Test
    void connect() throws SQLException {
        final String USERNAME = "schedulegnome";
        final String PASSWORD = "gnomepassword";
        final String CONNECTION_STRING = "jdbc:mysql://108.52.164.126:3306/gnomedb";
        Properties props = new Properties();
        props.put("user", USERNAME);
        props.put("password", PASSWORD);
        Connection conn = DriverManager.getConnection(CONNECTION_STRING, props);

        assertEquals(CONNECTION_STRING, conn.getMetaData().getURL());
    }

}
