package model;

import java.time.LocalDate;

public class ServiceHistory {
    private int serviceID;
    private String vehicleID;
    private LocalDate serviceDate;
    private long odometerReading;
    private String notes;

    public ServiceHistory() {}

    public ServiceHistory(String vehicleID, LocalDate serviceDate, long odometerReading, String notes) {
        this.vehicleID = vehicleID;
        this.serviceDate = serviceDate;
        this.odometerReading = odometerReading;
        this.notes = notes;
    }

    // Getters and Setters
    public int getServiceID() { return serviceID; }
    public void setServiceID(int serviceID) { this.serviceID = serviceID; }

    public String getVehicleID() { return vehicleID; }
    public void setVehicleID(String vehicleID) { this.vehicleID = vehicleID; }

    public LocalDate getServiceDate() { return serviceDate; }
    public void setServiceDate(LocalDate serviceDate) { this.serviceDate = serviceDate; }

    public long getOdometerReading() { return odometerReading; }
    public void setOdometerReading(long odometerReading) { this.odometerReading = odometerReading; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
