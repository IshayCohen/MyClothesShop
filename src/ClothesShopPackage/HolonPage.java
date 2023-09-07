package ClothesShopPackage;

import ProductPackage.Product;
import ProductPackage.ProductDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import ClothesShop.StoreClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HolonPage extends JFrame {
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField searchField;

    public HolonPage(String username) {
        setTitle("Welcome to Holon, " + username);
        setSize(800, 600);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);

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

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new BorderLayout());
        
        JButton logoutButton = new JButton("Logout");
        JButton openChatRoomButton = new JButton("Open Chat Room");
        
        // Styling for buttons
        Dimension buttonSize = new Dimension(160, 40);
        logoutButton.setPreferredSize(buttonSize);
        openChatRoomButton.setPreferredSize(buttonSize);

        // Add action listeners to buttons
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                StoreClient.start();
            }
        });

        openChatRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openChatRoom();
            }
        });

        // Create left and right panels for buttons
        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.add(openChatRoomButton);
        
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.add(logoutButton);

        // Add the left and right button panels to the main button panel
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);

        // Add components to the frame
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Helper method to perform the search
    private void performSearch() {
        String searchText = searchField.getText();
        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + searchText); // Case-insensitive search
        sorter.setRowFilter(rowFilter);
    }

    private void openChatRoom() {
        // Create and display the chat room page
        ChatRoomPage chatRoomPage = new ChatRoomPage();
        chatRoomPage.setVisible(true);
    }

}