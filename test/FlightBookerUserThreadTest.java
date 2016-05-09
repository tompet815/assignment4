/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import flightbooker.Controller;
import flightbooker.Reservation;
import flightbooker.UserThread;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomoe
 */
public class FlightBookerUserThreadTest {

    Reservation reservation;
    Controller con;
    private final String plane_no = "CR9";
    private final long CUSTOMERID = 1234;
    private final String USERNAME = "cphtp95";
    private final String PW = "cphtp95";

    @Before
    public void setUp() {
        reservation = new Reservation(USERNAME, PW);
        con = new Controller(USERNAME,PW);
        con.clearAllBookings(plane_no);
    }

    @Test
    public void testCall() throws SQLException, Exception {
        UserThread th = new UserThread(plane_no, CUSTOMERID, USERNAME, PW);
        int res = th.call();
        //this can be 0 or -3
        assertNotSame(-1, res);
        assertNotSame(-2, res);
        assertNotSame(-4, res);
        assertNotSame(-5, res);
    }
    @Test
    public void testCallWithAllBooked() throws SQLException, Exception {
        con.bookAll(plane_no);
        UserThread th = new UserThread(plane_no, CUSTOMERID, USERNAME, PW);
        int res = th.call();
        assertEquals(-100, res);
    }

}
