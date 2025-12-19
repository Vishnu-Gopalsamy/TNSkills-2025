package ui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("FLEET MANAGEMENT SOLUTIONS");
        setSize(520, 580);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        tabs.add("⚙ Items", new CrudPanel());
        tabs.add("💰 Sales", new SalesPanel());
        tabs.add("  View All", new ViewDataPanel());
        tabs.add(" 📈 Charts", new SalesChartPanel());
        tabs.add("📊 Reports", new ReportPanel());

        add(tabs);
    }
}
