/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import flightbooker.Controller;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * controller unit test
 *
 * @author Tomoe
 */
public class FlightBookerControllerTest {

    Controller controller;
    private final String plane_no = "CR9";
    private final String USERNAME = "cphtp95";
    private final String PW = "cphtp95";

    @Before
    public void setUp() {
        controller = new Controller(USERNAME, PW);
        controller.clearAllBookings(plane_no);
    }

    @Test
    public void testBookAll() {

        controller.bookAll(plane_no);
        boolean isAllBooked = controller.isAllBooked(plane_no);
        assertTrue(isAllBooked);

    }

    @Test
    public void testCrearAll() {

        controller.bookAll(plane_no);
        boolean isAllBooked = controller.isAllBooked(plane_no);

        controller.clearAllBookings(plane_no);
        boolean isAllBookedAfterClearing = controller.isAllBooked(plane_no);
        assertTrue(isAllBooked);
        assertFalse(isAllBookedAfterClearing);

    }

    @Test
    public void testAllReserved() {

        boolean isAllReservedBeforeBookAll = controller.isAllReserved(plane_no);
        controller.bookAll(plane_no);
        boolean isAllReserved = controller.isAllReserved(plane_no);
        assertFalse(isAllReservedBeforeBookAll);
        assertTrue(isAllReserved);

    }
}
