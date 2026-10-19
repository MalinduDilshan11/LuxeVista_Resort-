package com.example.hotel;

public class Booking {
    private String roomID;
    private String roomName;
    private String roomPrice;
    private String email;
    private String checkInDate;
    private String checkOutDate;

    // Constructor
    public Booking(String roomID, String roomName, String roomPrice, String email, String checkInDate, String checkOutDate) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomPrice = roomPrice;
        this.email = email;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Getters
    public String getRoomID() { return roomID; }
    public String getRoomName() { return roomName; }
    public String getRoomPrice() { return roomPrice; }
    public String getEmail() { return email; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckOutDate() { return checkOutDate; }
}
