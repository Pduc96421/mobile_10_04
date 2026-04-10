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
    
    // Display fields
    private String movieTitle;
    private String seatsDisplay;
    private String showtimeDate;

    public Ticket() {
    }

    public Ticket(String id, String userId, String showtimeId, List<String> seatNumbers, double totalAmount, long bookingTime, String movieTitle, String seatsDisplay, String showtimeDate) {
        this.id = id;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatNumbers = seatNumbers;
        this.totalAmount = totalAmount;
        this.bookingTime = bookingTime;
        this.movieTitle = movieTitle;
        this.seatsDisplay = seatsDisplay;
        this.showtimeDate = showtimeDate;
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
    
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public String getSeatsDisplay() { return seatsDisplay; }
    public void setSeatsDisplay(String seatsDisplay) { this.seatsDisplay = seatsDisplay; }
    public String getShowtimeDate() { return showtimeDate; }
    public void setShowtimeDate(String showtimeDate) { this.showtimeDate = showtimeDate; }
}
