/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightbooker;

/**
 *
 * @author Tomoe
 */
public class Seat {
    private String plane_no;
    private String seat_no;
    private long reserved;

    @Override
    public String toString() {
        return "Seat{" + "plane_no=" + plane_no + ", seat_no=" + seat_no + ", reserved=" + reserved + ", booked=" + booked + ", booking_time=" + booking_time + '}';
    }
    private long booked;
    private long booking_time;
    
    public Seat (String plane_no, String seat_no, long reserved, long booked, long booking_time){
    this.plane_no=plane_no;
    this.seat_no=seat_no;
    this.reserved=reserved;
    this.booked=booked;
    this.booking_time=booking_time;
        }

    public String getPlane_no() {
        return plane_no;
    }

    public void setPlane_no(String plane_no) {
        this.plane_no = plane_no;
    }

    public String getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    public long getReserved() {
        return reserved;
    }

    public void setReserved(long reserved) {
        this.reserved = reserved;
    }

    public long getBooked() {
        return booked;
    }

    public void setBooked(long booked) {
        this.booked = booked;
    }

    public long getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(long booking_time) {
        this.booking_time = booking_time;
    }
    
    
    
    
}
