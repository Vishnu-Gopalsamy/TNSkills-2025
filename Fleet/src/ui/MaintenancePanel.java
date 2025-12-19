package ui;

import dao.MaintenanceAlertDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class MaintenancePanel extends JPanel {

    private MaintenanceAlertDAO alertDAO = new MaintenanceAlertDAO();
    
    private JTable alertsTable;
    private DefaultTableModel alertsTableModel;
    private JTable vehiclesTable;
    private DefaultTableModel vehiclesTableModel;
    
    public MaintenancePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("Maintenance Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // Main content with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        
        // Bottom panel with actions
        JPanel bottomPanel = new JPanel(new FlowLayout());
        
       
        
    }
    }
