package dao;

import db.DBConnection;
import model.Trip;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TripDAO {
    
    public List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        String query = "SELECT * FROM Trips ORDER BY StartDate DESC";
       
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Trip trip = new Trip();
                trip.setTripID(rs.getInt("TripID"));
                trip.setVehicleID(rs.getString("VehicleID"));
                trip.setDriverName(rs.getString("DriverName"));
                trip.setStartDate(LocalDate.parse(rs.getString("StartDate")));
                trip.setEndDate(LocalDate.parse(rs.getString("EndDate")));
                trip.setDistanceKm(rs.getLong("DistanceKm"));
                trips.add(trip);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving trips: " + e.getMessage());
            e.printStackTrace();
        }
        
        return trips;
    }
    
    public List<Trip> getTripsByVehicle(String vehicleID) {
        List<Trip> trips = new ArrayList<>();
        String query = "SELECT * FROM Trips WHERE VehicleID = ? ORDER BY StartDate DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, vehicleID);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Trip trip = new Trip();
                trip.setTripID(rs.getInt("TripID"));
                trip.setVehicleID(rs.getString("VehicleID"));
                trip.setDriverName(rs.getString("DriverName"));
                trip.setStartDate(LocalDate.parse(rs.getString("StartDate")));
                trip.setEndDate(LocalDate.parse(rs.getString("EndDate")));
                trip.setDistanceKm(rs.getLong("DistanceKm"));
                trips.add(trip);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving trips for vehicle: " + e.getMessage());
            e.printStackTrace();
        }
        
        return trips;
    }
    
    public boolean addTrip(Trip trip) {
        String query = "INSERT INTO Trips (VehicleID, DriverName, StartDate, EndDate, DistanceKm) " +
                      "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, trip.getVehicleID());
            pstmt.setString(2, trip.getDriverName());
            pstmt.setString(3, trip.getStartDate().toString());
            pstmt.setString(4, trip.getEndDate().toString());
            pstmt.setLong(5, trip.getDistanceKm());
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error adding trip: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean hasConflict(String vehicleID, LocalDate startDate, LocalDate endDate) {
        String query = "SELECT COUNT(*) as count FROM Trips WHERE VehicleID = ? AND (" +
                      "(StartDate <= ? AND EndDate >= ?) OR " +  
                      "(StartDate >= ? AND EndDate <= ?) OR " +   
                      "(StartDate <= ? AND EndDate >= ?) OR " +   
                      "(StartDate <= ? AND EndDate >= ?))";    
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, vehicleID);
            pstmt.setString(2, endDate.toString());
            pstmt.setString(3, startDate.toString());
            pstmt.setString(4, startDate.toString());
            pstmt.setString(5, endDate.toString());
            pstmt.setString(6, startDate.toString());
            pstmt.setString(7, startDate.toString());
            pstmt.setString(8, endDate.toString());
            pstmt.setString(9, endDate.toString());
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (Exception e) {
            System.err.println("Error checking conflict: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public List<String> getAvailableVehicles(LocalDate startDate, LocalDate endDate) {
        List<String> availableVehicles = new ArrayList<>();
        String query = "SELECT VehicleID FROM Vehicles WHERE VehicleID NOT IN (" +
                      "SELECT DISTINCT VehicleID FROM Trips WHERE " +
                      "(StartDate <= ? AND EndDate >= ?) OR " +
                      "(StartDate >= ? AND EndDate <= ?) OR " +
                      "(StartDate <= ? AND EndDate >= ?) OR " +
                      "(StartDate <= ? AND EndDate >= ?))";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, endDate.toString());
            pstmt.setString(2, startDate.toString());
            pstmt.setString(3, startDate.toString());
            pstmt.setString(4, endDate.toString());
            pstmt.setString(5, startDate.toString());
            pstmt.setString(6, startDate.toString());
            pstmt.setString(7, endDate.toString());
            pstmt.setString(8, endDate.toString());
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                availableVehicles.add(rs.getString("VehicleID"));
            }
        } catch (Exception e) {
            System.err.println("Error finding available vehicles: " + e.getMessage());
            e.printStackTrace();
        }
        
        return availableVehicles;
    }
    
    public boolean isOnTrip(String vehicleID, LocalDate date) {
        String query = "SELECT COUNT(*) as count FROM Trips WHERE VehicleID = ? " +
                      "AND StartDate <= ? AND EndDate >= ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, vehicleID);
            pstmt.setString(2, date.toString());
            pstmt.setString(3, date.toString());
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (Exception e) {
            System.err.println("Error checking trip status: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}
