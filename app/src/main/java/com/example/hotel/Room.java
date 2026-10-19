package com.example.hotel;

public class Room {
    private int roomID;
    private String roomName;
    private int roomCapacity;
    private String roomDescription;
    private double roomPrice;
    private byte[] roomImage;

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public byte[] getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(byte[] roomImage) {
        this.roomImage = roomImage;
    }
}
