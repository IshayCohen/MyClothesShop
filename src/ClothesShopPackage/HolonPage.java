package ClothesShopPackage;

import ProductPackage.Product;
import ProductPackage.ProductDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HolonPage extends JFrame {
    public HolonPage(String username) {
        setTitle("Welcome to Holon, " + username);
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Load products for Holon branch
        List<Product> products = ProductDatabase.loadProducts("Holon");

        // Create a table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Type");
        tableModel.addColumn("Name");
        tableModel.addColumn("Size");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");

        for (Product product : products) {
            Object[] row = {product.getType(), product.getName(), product.getSize(), product.getQuantity(), product.getPrice()};
            tableModel.addRow(row);
        }

        // Create the table
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
    }
}
