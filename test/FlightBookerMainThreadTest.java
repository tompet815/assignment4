/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import flightbooker.Controller;
import flightbooker.MasterThread;
import flightbooker.Reservation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * this test class is just for running wholeprogram
 *
 * @author Tomoe
 */
public class FlightBookerMainThreadTest {

    Controller con;
    private final String plane_no = "CR9";
    private final String USERNAME = "cphtp95";
    private final String PW = "cphtp95";

    @Before
    public void setUp() {

        con = new Controller(USERNAME, PW);
        con.clearAllBookings(plane_no);
    }

    @Test
    public void testRun() {
        MasterThread th = new MasterThread();
        th.run();

    }

}
