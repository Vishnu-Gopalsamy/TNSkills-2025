package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static volatile Connection connection;
    private static final String DB_URL = "jdbc:sqlite:fleet_management.db";

    private DBConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DBConnection.class) {
                if (connection == null) {
                    try {
                        Class.forName("org.sqlite.JDBC");
                        connection = DriverManager.getConnection(DB_URL);
                        System.out.println("Connected to SQLite database successfully");
                        
                        initializeDatabase();
                        
                    } catch (Exception e) {
                        System.err.println("Database connection error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }
    
    private static void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            // Create Vehicles table
            String createVehiclesTable = "CREATE TABLE IF NOT EXISTS Vehicles (" +
                    "VehicleID VARCHAR(50) PRIMARY KEY, " +
                    "RegNo VARCHAR(50) NOT NULL, " +
                    "Type VARCHAR(20) NOT NULL, " +
                    "LastServiceDate DATE NOT NULL, " +
                    "CurrentOdometer BIGINT NOT NULL, " +
                    "LastServiceOdometer BIGINT NOT NULL)";
            stmt.executeUpdate(createVehiclesTable);
            
            // Create Trips table
            String createTripsTable = "CREATE TABLE IF NOT EXISTS Trips (" +
                    "TripID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "VehicleID VARCHAR(50) NOT NULL, " +
                    "DriverName VARCHAR(100) NOT NULL, " +
                    "StartDate DATE NOT NULL, " +
                    "EndDate DATE NOT NULL, " +
                    "DistanceKm BIGINT NOT NULL, " +
                    "FOREIGN KEY (VehicleID) REFERENCES Vehicles(VehicleID))";
            stmt.executeUpdate(createTripsTable);
            
            // Create Maintenance_Alerts table
            String createAlertsTable = "CREATE TABLE IF NOT EXISTS Maintenance_Alerts (" +
                    "AlertID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "VehicleID VARCHAR(50) NOT NULL, " +
                    "AlertDate DATE NOT NULL, " +
                    "Reason VARCHAR(255) NOT NULL, " +
                    "FOREIGN KEY (VehicleID) REFERENCES Vehicles(VehicleID))";
            stmt.executeUpdate(createAlertsTable);
            
            // Create Service_History table
            String createServiceHistoryTable = "CREATE TABLE IF NOT EXISTS Service_History (" +
                    "ServiceID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "VehicleID VARCHAR(50) NOT NULL, " +
                    "ServiceDate DATE NOT NULL, " +
                    "OdometerReading BIGINT NOT NULL, " +
                    "Notes VARCHAR(500), " +
                    "FOREIGN KEY (VehicleID) REFERENCES Vehicles(VehicleID))";
            stmt.executeUpdate(createServiceHistoryTable);
            
            System.out.println("All tables created/verified successfully");
            
            // Check if we need to seed data
            seedInitialData();
            
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void seedInitialData() {
        try (Statement stmt = connection.createStatement()) {
            // Check if data already exists
            var rs = stmt.executeQuery("SELECT COUNT(*) as count FROM Vehicles");
            if (rs.next() && rs.getInt("count") == 0) {
                // Insert seed data
                stmt.executeUpdate("INSERT INTO Vehicles VALUES " +
                        "('V1', 'REG-001', 'Truck', '2025-05-20', 20000, 10000), " +
                        "('V_001', 'REG-101', 'Van', '2025-10-01', 50400, 40400), " +
                        "('V_002', 'REG-102', 'Car', '2025-11-15', 12050, 10000), " +
                        "('V_003', 'REG-103', 'Van', '2025-08-20', 8200, 2000)");
                
                stmt.executeUpdate("INSERT INTO Trips (VehicleID, DriverName, StartDate, EndDate, DistanceKm) " +
                        "VALUES ('V1', 'TestDriver', '2025-12-20', '2025-12-30', 500)");
                
                System.out.println("Seed data inserted successfully");
            }
        } catch (Exception e) {
            System.err.println("Error seeding data: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (Exception e) {
                System.err.println("Error closing connection: " + e.getMessage());
                System.err.println("Error closing connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
