/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightbooker;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Tomoe
 */
public class DB {
 private static final String USERNAME = "cphtp95";
    private static final String PW = "cphtp95";
    private static Connection conn = null;

    public static Connection getConnection() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat", USERNAME, PW);

        }
        catch (Exception e) {
            System.out.println("Error in DB.prepare()");
            System.out.println(e);
        }
        return conn;

    }

    public static void releaseConnection(Connection conn) {
        try {
            conn.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

}
