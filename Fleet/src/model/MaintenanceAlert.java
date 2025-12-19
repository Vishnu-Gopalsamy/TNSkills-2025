package model;

import java.time.LocalDate;

public class MaintenanceAlert {
    private int alertID;
    private String vehicleID;
    private LocalDate alertDate;
    private String reason;

    public MaintenanceAlert() {}

    public MaintenanceAlert(String vehicleID, LocalDate alertDate, String reason) {
        this.vehicleID = vehicleID;
        this.alertDate = alertDate;
        this.reason = reason;
    }

    public int getAlertID() { return alertID; }
    public void setAlertID(int alertID) { this.alertID = alertID; }

    public String getVehicleID() { return vehicleID; }
    public void setVehicleID(String vehicleID) { this.vehicleID = vehicleID; }

    public LocalDate getAlertDate() { return alertDate; }
    public void setAlertDate(LocalDate alertDate) { this.alertDate = alertDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
