package ui;

import dao.VehicleDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import pdf.ReportGenerator;

public class ReportPanel extends JPanel {

    public ReportPanel() {
        setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Report Generation");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(20, 20, 400, 30);

        // Description
        JLabel descLabel = new JLabel("Generate PDF reports for Items and Sales.");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setBounds(20, 60, 450, 25);

        // Items Report Panel
        JPanel itemsPanel = new JPanel();
        itemsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2), 
            "Items Report"));
        itemsPanel.setBounds(20, 100, 220, 150);
        itemsPanel.setLayout(null);

        JLabel itemsDesc = new JLabel("<html>All items with:<br>• ID<br>• Name<br>• Price</html>");
        itemsDesc.setBounds(15, 25, 190, 60);
        itemsPanel.add(itemsDesc);

        JButton generateItems = new JButton("Generate Items PDF");
        generateItems.setBounds(15, 95, 180, 35);
        generateItems.setBackground(new Color(52, 152, 219));
        generateItems.setForeground(Color.WHITE);
        generateItems.setFont(new Font("Arial", Font.BOLD, 12));
        itemsPanel.add(generateItems);

        // Sales Report Panel
        JPanel salesPanel = new JPanel();
        salesPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(46, 204, 113), 2), 
            "Sales Report"));
        salesPanel.setBounds(260, 100, 220, 150);
        salesPanel.setLayout(null);

        JLabel salesDesc = new JLabel("<html>All sales with:<br>• Item Name<br>• Quantity<br>• Revenue<br>• Date</html>");
        salesDesc.setBounds(15, 25, 190, 80);
        salesPanel.add(salesDesc);

        JButton generateSales = new JButton("Generate Sales PDF");
        generateSales.setBounds(15, 95, 180, 35);
        generateSales.setBackground(new Color(46, 204, 113));
        generateSales.setForeground(Color.WHITE);
        generateSales.setFont(new Font("Arial", Font.BOLD, 12));
        salesPanel.add(generateSales);

        add(titleLabel);
        add(descLabel);
        add(itemsPanel);
        add(salesPanel);

        // Status Label
        JLabel statusLabel = new JLabel("");
        statusLabel.setBounds(20, 270, 450, 50);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        statusLabel.setForeground(Color.GRAY);
        add(statusLabel);

        // Items Report Button Action
        generateItems.addActionListener(e -> {
            try {
                List<model.Vehicle> vehicle = new VehicleDAO().getAll();
                
                if (vehicle.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "No items available to generate report!\nPlease add items first.", 
                        "No Data", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                statusLabel.setText("Generating report...");
                statusLabel.setForeground(java.awt.Color.BLUE);
                
                ReportGenerator.generate(vehicle);
                
                statusLabel.setText("Report generated successfully! File: report.pdf");
                statusLabel.setForeground(new java.awt.Color(0, 128, 0));
                
                JOptionPane.showMessageDialog(this, 
                    "PDF Report Generated Successfully!\n\nFile: report.pdf\nTotal Items: " + items.size(), 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                statusLabel.setText("Error generating report: " + ex.getMessage());
                statusLabel.setForeground(java.awt.Color.RED);
                
                JOptionPane.showMessageDialog(this, 
                    "Failed to generate report:\n" + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
