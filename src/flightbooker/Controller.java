package flightbooker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tomoe
 */
public class Controller {

  private Connection conn = null;
  private String username;
  private String pw;

   private long testID=-9999;

    public Controller(String username, String pw) {
        this.username=username;
        this.pw=pw;

    }

    public void bookAll(String plane_no) {
        conn = DB.getConnection();

        String SQLString
                = "update seat "
                + "set reserved = ?, booked = ?, booking_time = ? "
                + "where plane_no = ? ";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            statement.setLong(1, testID);
            statement.setLong(2, testID);
            statement.setLong(3, new Date().getTime());
            statement.setString(4, plane_no);
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        finally {
            DB.releaseConnection(conn);
        }

    }

    public boolean isAllBooked(String plane_no) {

        conn = DB.getConnection();

        String SQLString
                = "select * from seat "
                + "where booked IS null "
                + "and plane_no = ? ";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);

            statement.setString(1, plane_no);
            ResultSet rs = statement.executeQuery();

            return !rs.next();

        }
        catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }

        finally {
            DB.releaseConnection(conn);
        }
    }

    public boolean isAllReserved(String plane_no) {

        conn = DB.getConnection();

        String SQLString
                = "select * from seat "
                + "where reserved IS NOT null "
                + "and plane_no = ? ";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);

            statement.setString(1, plane_no);
            ResultSet rs = statement.executeQuery();
            return rs.next();

        }
        catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }

        finally {
            DB.releaseConnection(conn);
        }
    }

    public void clearAllBookings(String plane_no) {

        conn = DB.getConnection();

        String SQLString
                = "update seat "
                + "set booked = null, reserved=null, booking_time=null "
                + "where plane_no = ? ";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            statement.setString(1, plane_no);
            statement.executeUpdate();

        }
        catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);

        }

        finally {
            DB.releaseConnection(conn);
        }
    }

}
