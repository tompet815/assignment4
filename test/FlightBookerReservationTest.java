/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import flightbooker.Controller;
import flightbooker.Reservation;
import flightbooker.Seat;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomoe Test for reservation
 */
public class FlightBookerReservationTest {

    Reservation reservation;
    Controller con;
    private final String plane_no = "CR9";
    private final String USERNAME = "cphtp95";
    private final String PW = "cphtp95";

    @Before
    public void setUp() {
        reservation = new Reservation(USERNAME, PW);
        con = new Controller(USERNAME, PW);
        con.clearAllBookings(plane_no);
    }

 
    @Test
    public void testReserve() throws SQLException {
        Seat seat = reservation.reserve(plane_no, 1234);
        assertNotNull(seat);
        long reservedID = seat.getReserved();
        assertEquals(1234, reservedID);
    }

    @Test
    public void testBook() throws SQLException {
        Seat seat = reservation.reserve(plane_no, 1234);
        int res = reservation.book(plane_no, seat.getSeat_no(), seat.getReserved());
        assertEquals(0, res);

    }

}
