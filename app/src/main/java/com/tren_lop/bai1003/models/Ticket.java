package com.tren_lop.bai1003.models;

import java.io.Serializable;
import java.util.List;

public class Ticket implements Serializable {
    private String id;
    private String userId;
    private String showtimeId;
    private List<String> seatNumbers;
    private double totalAmount;
    private long bookingTime;

    public Ticket() {
    }

    public Ticket(String id, String userId, String showtimeId, List<String> seatNumbers, double totalAmount, long bookingTime) {
        this.id = id;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatNumbers = seatNumbers;
        this.totalAmount = totalAmount;
        this.bookingTime = bookingTime;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getShowtimeId() { return showtimeId; }
    public void setShowtimeId(String showtimeId) { this.showtimeId = showtimeId; }
    public List<String> getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(List<String> seatNumbers) { this.seatNumbers = seatNumbers; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public long getBookingTime() { return bookingTime; }
    public void setBookingTime(long bookingTime) { this.bookingTime = bookingTime; }
}
