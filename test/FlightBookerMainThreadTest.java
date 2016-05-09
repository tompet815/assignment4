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
 * this test class is just for running whole program
 * @author Tomoe
 */
public class FlightBookerMainThreadTest {

    @Test
    public void testRun() {
        MasterThread th = new MasterThread();
        th.run();
      
    }

}
