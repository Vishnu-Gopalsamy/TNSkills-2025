package dao;

import db.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.ServiceHistory;

public class ServiceHistoryDAO {
    
    public List<ServiceHistory> getAllServiceHistory() {
        List<ServiceHistory> history = new ArrayList<>();
        String query = "SELECT * FROM Service_History ORDER BY ServiceDate DESC";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                ServiceHistory service = new ServiceHistory();
                service.setServiceID(rs.getInt("ServiceID"));
                service.setVehicleID(rs.getString("VehicleID"));
                service.setServiceDate(LocalDate.parse(rs.getString("ServiceDate")));
                service.setOdometerReading(rs.getLong("OdometerReading"));
                service.setNotes(rs.getString("Notes"));
                history.add(service);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving service history: " + e.getMessage());
            e.printStackTrace();
        }
        
        return history;
    }
    
    public List<ServiceHistory> getServiceHistoryByVehicle(String vehicleID) {
        List<ServiceHistory> history = new ArrayList<>();
        String query = "SELECT * FROM Service_History WHERE VehicleID = ? ORDER BY ServiceDate DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, vehicleID);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ServiceHistory service = new ServiceHistory();
                service.setServiceID(rs.getInt("ServiceID"));
                service.setVehicleID(rs.getString("VehicleID"));
                service.setServiceDate(LocalDate.parse(rs.getString("ServiceDate")));
                service.setOdometerReading(rs.getLong("OdometerReading"));
                service.setNotes(rs.getString("Notes"));
                history.add(service);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving service history for vehicle: " + e.getMessage());
            e.printStackTrace();
        }
        
        return history;
    }
    
    public boolean addServiceRecord(ServiceHistory service) {
        String query = "INSERT INTO Service_History (VehicleID, ServiceDate, OdometerReading, Notes) " +
                      "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, service.getVehicleID());
            pstmt.setString(2, service.getServiceDate().toString());
            pstmt.setLong(3, service.getOdometerReading());
            pstmt.setString(4, service.getNotes());
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error adding service record: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}
