package ClothesShopPackage;

import ProductPackage.Product;
import ProductPackage.ProductDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.RowFilter;
import java.util.List;

public class HolonPage extends JFrame {
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField searchField;

    public HolonPage(String username) {
        setTitle("Welcome to Holon, " + username);
        setSize(800, 600); // Increase the frame size for a better view
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Load products for Holon branch
        List<Product> products = ProductDatabase.loadProducts("Holon");

        // Create a table model
        tableModel = new DefaultTableModel();
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

        // Create a TableRowSorter for sorting
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        // Add a search field
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        // Create a panel for search components
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add components to the frame
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        add(logoutButton, BorderLayout.SOUTH);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform logout action here
                dispose(); // Close the branch page
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
            }
        });
    }

    // Helper method to perform the search
    private void performSearch() {
        String searchText = searchField.getText();
        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + searchText); // Case-insensitive search
        sorter.setRowFilter(rowFilter);
    }
}
