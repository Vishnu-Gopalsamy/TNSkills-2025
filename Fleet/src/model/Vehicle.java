package model;

import java.time.LocalDate;

public class Vehicle {
    private String vehicleID;
    private String regNo;
    private String type;
    private LocalDate lastServiceDate;
    private long currentOdometer;
    private long lastServiceOdometer;

    public Vehicle() {}

    public Vehicle(String vehicleID, String regNo, String type, LocalDate lastServiceDate, 
                   long currentOdometer, long lastServiceOdometer) {
        this.vehicleID = vehicleID;
        this.regNo = regNo;
        this.type = type;
        this.lastServiceDate = lastServiceDate;
        this.currentOdometer = currentOdometer;
        this.lastServiceOdometer = lastServiceOdometer;
    }

     public String getVehicleID() { return vehicleID; }
    public void setVehicleID(String vehicleID) { this.vehicleID = vehicleID; }

    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDate getLastServiceDate() { return lastServiceDate; }
    public void setLastServiceDate(LocalDate lastServiceDate) { this.lastServiceDate = lastServiceDate; }

    public long getCurrentOdometer() { return currentOdometer; }
    public void setCurrentOdometer(long currentOdometer) { this.currentOdometer = currentOdometer; }

    public long getLastServiceOdometer() { return lastServiceOdometer; }
    public void setLastServiceOdometer(long lastServiceOdometer) { this.lastServiceOdometer = lastServiceOdometer; }

}
