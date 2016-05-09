/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightbooker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomoe
 */
public class Reservation {

    private Connection conn;
    private String username;
    private String pw;

    public Reservation(String user, String pw) {
        this.username = user;
        this.pw = pw;
    }

    public Seat reserve(String plane_no, long id) throws SQLException {

        PreparedStatement statement = null;
        conn = DB.getConnection();
        try {
            conn.setAutoCommit(false);
            conn.setSavepoint();
            int rowUpdated = 0;
            String seat_no = "";
            long booking_time = 0;

            String SQLString
                    = "select seat_no, booking_time "
                    + "from seat "
                    + "where plane_no = ? AND booked is null "
                    + "AND (reserved is null OR " + new Date().getTime() + "-booking_time>5000) ";
         

            String updateSQLStringBase = "update seat "
                    + "set reserved = ? , booking_time = ? "
                    + "where plane_no = ? AND booked is null "
                    + "AND seat_no=? " ;
            statement = conn.prepareStatement(SQLString);
            statement.setString(1, plane_no);
            ResultSet rs = statement.executeQuery();
String updateSQLString="";
            while (rs.next()) {
                updateSQLString=updateSQLStringBase;
                seat_no = rs.getString(1);
                booking_time = rs.getLong(2);
                
                if (booking_time == 0) {
                   
                    updateSQLString+= "AND booking_time IS NULL";
                }
                else {
                    
                    updateSQLString += "AND booking_time = ?";
                }
                statement = conn.prepareStatement(updateSQLString);
                statement.setLong(1, id);
                statement.setLong(2, new Date().getTime());
                statement.setString(3, plane_no);
                statement.setString(4, seat_no);
                if (booking_time != 0) {
                    statement.setLong(5, booking_time);
                }
                rowUpdated = statement.executeUpdate();
                if (rowUpdated > 0) {
                    conn.commit();
                    DB.releaseConnection(conn);
                    return new Seat(plane_no, seat_no, id, 0, booking_time);
                }
            }
            conn.rollback();
            DB.releaseConnection(conn);
            return null;//no seat reservable
        }
        catch (SQLException ex) {
            Logger.getLogger(Reservation.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
            DB.releaseConnection(conn);
            return null;
        }

    }

    public int book(String plane_no, String seat_no, long id) throws SQLException {
        try {
            conn = DB.getConnection();
            PreparedStatement statement = null;

            String SQLString
                    = "select reserved, booked, booking_time "
                    + "from seat "
                    + "where plane_no = ? "
                    + "AND seat_no = ? AND reserved = " + id;
            String SQLUpdateString
                    = "update seat "
                    + "set booking_time = ?, booked = ? "
                    + "where plane_no = ? AND seat_no = ? ";

            long reserved = 0;
            long booked = 0;
            long booking_time = 0;
            statement = conn.prepareStatement(SQLString);
            statement.setString(1, plane_no);
            statement.setString(2, seat_no);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                reserved = rs.getLong(1);
                booked = rs.getLong(2);
                booking_time = rs.getLong(3);
                if (reserved == 0) {
                    return -1;
                }
                else if (booked != 0) {
                    return -4;
                }
                else if (reserved != id) {
                    resetSeat(conn, plane_no, seat_no);
                    return -2;
                }
                else if (new Date().getTime() - booking_time > 5000) {

                    return -3;
                }

                statement = conn.prepareStatement(SQLUpdateString);
                statement.setLong(1, new Date().getTime());
                statement.setLong(2, id);
                statement.setString(3, plane_no);
                statement.setString(4, seat_no);

                if (statement.executeUpdate() == 1) {

                    return 0;
                }
                else {
                    return -5;
                }

            }
            else {

                return -4;//already reserved
            }
        }

        catch (SQLException ex) {

            Logger.getLogger(Reservation.class.getName()).log(Level.SEVERE, null, ex);

            return -5;
        }
        finally {
            DB.releaseConnection(conn);
        }
    }

    private void resetSeat(Connection conn, String plane_no, String seat_no) {

        try {
            PreparedStatement statement = null;
            String SQLClearString
                    = "update seat "
                    + "set booking_time = null, booked = null, reserved=null "
                    + "where plane_no = ? AND seat_no = ? ";

            statement = conn.prepareStatement(SQLClearString);
            statement.setString(1, plane_no);
            statement.setString(2, seat_no);
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(Reservation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
