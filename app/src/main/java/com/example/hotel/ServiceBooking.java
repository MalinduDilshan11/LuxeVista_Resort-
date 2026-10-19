package com.example.hotel;

public class ServiceBooking {
    private String serviceName;
    private String reserveDate;
    private double servicePrice;
    private String email;

    // No-argument constructor
    public ServiceBooking() {
        // You can set default values here if necessary
    }

    // Constructor with parameters
    public ServiceBooking(String serviceName, String reserveDate, double servicePrice, String email) {
        this.serviceName = serviceName;
        this.reserveDate = reserveDate;
        this.servicePrice = servicePrice;
        this.email = email;
    }

    // Getters and Setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}



