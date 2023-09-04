package ClothesShopPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AllProductsPage extends JFrame {
    public AllProductsPage() {
        // Read data from the "products.txt" file
        String fileName = "products.txt";
        String database = readFile(fileName);

        // Split the database into individual items
        String[] items = database.split("\n");

        // Create a JTable to display the data
        String[] columnNames = {"Type", "Name", "Size", "Quantity", "Price", "Location"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        for (String item : items) {
            String[] parts = item.split(":");
            String type = parts[0];
            String name = parts[1];
            String size = parts[2];
            int quantity = Integer.parseInt(parts[3]);
            double price = Double.parseDouble(parts[4]);
            String location = parts[5];

            model.addRow(new Object[]{type, name, size, quantity, "$" + price, location});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Set frame size and make it visible
        setTitle("All Products");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        setSize(800, 400);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    // Function to read the contents of a file and return as a string using BufferedReader
    private static String readFile(String fileName) {
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
