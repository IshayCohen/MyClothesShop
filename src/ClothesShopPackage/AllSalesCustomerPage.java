package ClothesShopPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AllSalesCustomerPage extends JFrame {
    private String username;

    public AllSalesCustomerPage(String username) {
        this.username = username;

        setTitle("All Sales");
        setSize(800, 400);

        // Create a JTable to display the sales data
        String[] columnNames = {"Type", "Name", "Size", "Quantity", "Price", "Branch"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        // Read sales data from "sales.txt" and populate the table
        String fileName = "sales.txt";
        String salesData = readFile(fileName);
        String[] salesItems = salesData.split("\n");

        for (String item : salesItems) {
            String[] parts = item.split(":");
            String type = parts[0];
            String name = parts[1];
            String size = parts[2];
            int quantity = Integer.parseInt(parts[3]);
            double price = Double.parseDouble(parts[4]);
            String branch = parts[5];

            model.addRow(new Object[]{type, name, size, quantity, "$" + price, branch});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String readFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
