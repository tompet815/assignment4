/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightbooker;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 *
 * @author Tomoe
 */
public class UserThread implements Callable<Integer> {

    private long customerID;
    private String planeno;
    private Reservation reservation;
    private int result;

    public UserThread(String planeno, long customerID, String username, String pw) {
        this.customerID = customerID;
        this.planeno = planeno;
        this.reservation = new Reservation(username, pw);

    }

    @Override
    public Integer call() throws Exception {
        int res = -100;
        Random ran = new Random();
        int pause = ran.nextInt(11);
        Seat seat = reservation.reserve(planeno, customerID);
        Thread.sleep(pause * 1000);
        int decision = ran.nextInt(101);
        if (seat != null && decision > 25) {

            res = reservation.book(planeno, seat.getSeat_no(), customerID);
        }

        return res;

    }

}
