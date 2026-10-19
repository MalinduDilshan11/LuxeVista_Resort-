package com.example.hotel;

public class Service {
    private int serID;
    private String serName;
    private String serDes;
    private Double serPrice;
    private byte[] serImage;

    public Service(int serviceID, String serviceName, double servicePrice) {
    }

    public Service() {

    }

    public int getSerID() {
        return serID;
    }

    public void setSerID(int serID) {
        this.serID = serID;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public String getSerDes() {
        return serDes;
    }

    public void setSerDes(String serDes) {
        this.serDes = serDes;
    }

    public Double getSerPrice() {
        return serPrice;
    }

    public void setSerPrice(Double serPrice) {
        this.serPrice = serPrice;
    }

    public byte[] getSerImage() {
        return serImage;
    }

    public void setSerImage(byte[] serImage) {
        this.serImage = serImage;
    }
}