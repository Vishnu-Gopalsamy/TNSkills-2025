package dao;

import db.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Vehicle;

public class VehicleDAO {
    
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM Vehicles";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleID(rs.getString("VehicleID"));
                vehicle.setRegNo(rs.getString("RegNo"));
                vehicle.setType(rs.getString("Type"));
                vehicle.setLastServiceDate(LocalDate.parse(rs.getString("LastServiceDate")));
                vehicle.setCurrentOdometer(rs.getLong("CurrentOdometer"));
                vehicle.setLastServiceOdometer(rs.getLong("LastServiceOdometer"));
                vehicles.add(vehicle);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving vehicles: " + e.getMessage());
            e.printStackTrace();
        }
        
        return vehicles;
    }
    
    public Vehicle getVehicleById(String vehicleID) {
        String query = "SELECT * FROM Vehicles WHERE VehicleID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, vehicleID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleID(rs.getString("VehicleID"));
                vehicle.setRegNo(rs.getString("RegNo"));
                vehicle.setType(rs.getString("Type"));
                vehicle.setLastServiceDate(LocalDate.parse(rs.getString("LastServiceDate")));
                vehicle.setCurrentOdometer(rs.getLong("CurrentOdometer"));
                vehicle.setLastServiceOdometer(rs.getLong("LastServiceOdometer"));
                return vehicle;
            }
        } catch (Exception e) {
            System.err.println("Error retrieving vehicle: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO Vehicles (VehicleID, RegNo, Type, LastServiceDate, " +
                      "CurrentOdometer, LastServiceOdometer) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, vehicle.getVehicleID());
            pstmt.setString(2, vehicle.getRegNo());
            pstmt.setString(3, vehicle.getType());
            pstmt.setString(4, vehicle.getLastServiceDate().toString());
            pstmt.setLong(5, vehicle.getCurrentOdometer());
            pstmt.setLong(6, vehicle.getLastServiceOdometer());
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean updateVehicle(Vehicle vehicle) {
        String query = "UPDATE Vehicles SET RegNo = ?, Type = ?, LastServiceDate = ?, " +
                      "CurrentOdometer = ?, LastServiceOdometer = ? WHERE VehicleID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, vehicle.getRegNo());
            pstmt.setString(2, vehicle.getType());
            pstmt.setString(3, vehicle.getLastServiceDate().toString());
            pstmt.setLong(4, vehicle.getCurrentOdometer());
            pstmt.setLong(5, vehicle.getLastServiceOdometer());
            pstmt.setString(6, vehicle.getVehicleID());
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean updateOdometerIfGreater(String vehicleID, long newOdometer) {
        String query = "UPDATE Vehicles SET CurrentOdometer = ? " +
                      "WHERE VehicleID = ? AND CurrentOdometer < ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setLong(1, newOdometer);
            pstmt.setString(2, vehicleID);
            pstmt.setLong(3, newOdometer);
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error updating odometer: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean deleteVehicle(String vehicleID) {
        String query = "DELETE FROM Vehicles WHERE VehicleID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, vehicleID);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error deleting vehicle: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}