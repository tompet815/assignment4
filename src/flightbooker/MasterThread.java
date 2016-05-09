/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightbooker;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomoe
 */
public class MasterThread implements Runnable {

    private final String USERNAME = "cphtp95";
    private final String PW = "cphtp95";
    private final String PLANE_NO = "CR9";
    int result0 = 0;
    int result1 = 0;//o -1 if the seat is not reserved (it should not be possible to book a seat without a
    int result2 = 0;//o -2 if the seat is not reserved by the customer id (a seat can only be booked by the
    int result3 = 0;//o -3 reservation timeout, i.e. user to slow to make a booking
    int result4 = 0;//o -4 if the seat is already occupied (that’s not good since this is a overbooking)
    int result5 = 0;//o -5 for all other errors
    Controller con = new Controller(USERNAME, PW);

    @Override
    public void run() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 20, TimeUnit.SECONDS, new ArrayBlockingQueue(5));
        int i = 1;
        ArrayList<Future<Integer>> resList = new ArrayList();
        while (!con.isAllBooked(PLANE_NO)) {

            Future<Integer> res = executor.submit(new UserThread(PLANE_NO, i,USERNAME, PLANE_NO));

            if (res != null) {
                resList.add(res);

                i++;
            }

        }
        for (Future<Integer> res : resList) {

            try {
                switch (res.get()) {
                    case 0:
                        result0++;
                        break;
                    case -1:
                        result1++;
                        break;
                    case -2:
                        result2++;
                        break;
                    case -3:
                        result3++;
                        break;
                    case -4:
                        result4++;
                        break;
                    case -5:
                        result5++;
                        break;
                    default:
                        break;

                }
            }
            catch (InterruptedException ex) {
                Logger.getLogger(MasterThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (ExecutionException ex) {
                Logger.getLogger(MasterThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("-----------------------------result---------------------------");
        System.out.println("Number of UserThreads started : " + (i-1));
        System.out.println("Number of successful bookings : " + result0);
        System.out.println("Number of bookings where the customer was not the one that hold the\n"
                + "//reservation : " + result2);
        System.out.println("Number of bookings without a reservation : " + result1);
        System.out.println("Number of bookings of occupied seats : " + result5);
        executor.shutdown();
    }

}
