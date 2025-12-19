package ui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Fleet Management System");
        setSize(520, 580);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        tabs.add("⚙ Items", new MaintenancePanel());

        add(tabs);
    }
}
