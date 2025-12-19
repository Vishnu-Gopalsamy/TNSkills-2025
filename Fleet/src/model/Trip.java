package model;

import java.time.LocalDate;

public class Trip {
    private int tripID;
    private String vehicleID;
    private String driverName;
    private LocalDate startDate;
    private LocalDate endDate;
    private long distanceKm;

    public Trip() {}

    public Trip(String vehicleID, String driverName, LocalDate startDate, 
                LocalDate endDate, long distanceKm) {
        this.vehicleID = vehicleID;
        this.driverName = driverName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distanceKm = distanceKm;
    }

    // Getters and Setters
    public int getTripID() { return tripID; }
    public void setTripID(int tripID) { this.tripID = tripID; }

    public String getVehicleID() { return vehicleID; }
    public void setVehicleID(String vehicleID) { this.vehicleID = vehicleID; }


}
