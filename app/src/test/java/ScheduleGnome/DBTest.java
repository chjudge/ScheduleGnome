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

//    @Test
//    void insertCourse() {
//        Course crs = new Course(new String[] { "2020","10","ACCT","201","A",
//                "PRINCIPLES OF ACCOUNTING I","3","30","30","M","","W","","F","9:00:00 AM",
//                "9:50:00 AM","Stone","Jennifer","Nicole","Online materials fee" });
//            DBOperator db = new DBOperator();
//            db.insertCourse(crs);
//    }
}
