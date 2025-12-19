package dao;

import db.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.MaintenanceAlert;

public class MaintenanceAlertDAO {
    
    public List<MaintenanceAlert> getAllAlerts() {
        List<MaintenanceAlert> alerts = new ArrayList<>();
        String query = "SELECT * FROM Maintenance_Alerts ORDER BY AlertDate DESC";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                MaintenanceAlert alert = new MaintenanceAlert();
                alert.setAlertID(rs.getInt("AlertID"));
                alert.setVehicleID(rs.getString("VehicleID"));
                alert.setAlertDate(LocalDate.parse(rs.getString("AlertDate")));
                alert.setReason(rs.getString("Reason"));
                alerts.add(alert);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving alerts: " + e.getMessage());
            e.printStackTrace();
        }
        
        return alerts;
    }
    
    public List<MaintenanceAlert> getAlertsByVehicle(String vehicleID) {
        List<MaintenanceAlert> alerts = new ArrayList<>();
        String query = "SELECT * FROM Maintenance_Alerts WHERE VehicleID = ? ORDER BY AlertDate DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, vehicleID);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                MaintenanceAlert alert = new MaintenanceAlert();
                alert.setAlertID(rs.getInt("AlertID"));
                alert.setVehicleID(rs.getString("VehicleID"));
                alert.setAlertDate(LocalDate.parse(rs.getString("AlertDate")));
                alert.setReason(rs.getString("Reason"));
                alerts.add(alert);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving alerts for vehicle: " + e.getMessage());
            e.printStackTrace();
        }
        return alerts;
    }
    
    public boolean addAlert(MaintenanceAlert alert) {
        String query = "INSERT INTO Maintenance_Alerts (VehicleID, AlertDate, Reason) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, alert.getVehicleID());
            pstmt.setString(2, alert.getAlertDate().toString());
            pstmt.setString(3, alert.getReason());
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error adding alert: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    public boolean deleteAlert(int alertID) {
        String query = "DELETE FROM Maintenance_Alerts WHERE AlertID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, alertID);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error deleting alert: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    public boolean hasActiveAlert(String vehicleID) {
        String query = "SELECT COUNT(*) as count FROM Maintenance_Alerts WHERE VehicleID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, vehicleID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (Exception e) {
            System.err.println("Error checking active alerts: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    } 
    public void clearAlertsForVehicle(String vehicleID) {
        String query = "DELETE FROM Maintenance_Alerts WHERE VehicleID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, vehicleID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error clearing alerts: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
