package ClothesShopPackage;

import ProductPackage.Product;
import ProductPackage.ProductDatabase;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import ClothesShop.StoreClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HolonPage extends JFrame {
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField searchField;

    public HolonPage(String username) {
        setTitle("Welcome to branch Holon, " + username);
        setSize(800, 600);

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
        searchField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				  performSearch( searchField.getText(),sorter);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				  performSearch( searchField.getText(),sorter);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				  performSearch( searchField.getText(),sorter);
			}
		});
        

        // Create a panel for search components
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new BorderLayout());

        JButton logoutButton = new JButton("Logout");
        JButton openChatRoomButton = new JButton("Open Chat Room");
        JButton showStatisticsButton = new JButton("Show Store Statistics"); // New button

        // Styling for buttons
        Dimension buttonSize = new Dimension(160, 40);
        logoutButton.setPreferredSize(buttonSize);
        openChatRoomButton.setPreferredSize(buttonSize);
        showStatisticsButton.setPreferredSize(buttonSize); // Set size for the new button

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
                openChatRoom(username); // Pass the username
            }
        });

        showStatisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call a method to show store statistics (implement as needed)
                showStoreStatistics();
            }
        });

        // Create left, middle, and right panels for buttons
        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.add(openChatRoomButton);

        JPanel middleButtonPanel = new JPanel(); // New panel for the middle button
        middleButtonPanel.add(showStatisticsButton);

        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.add(logoutButton);

        // Add the left, middle, and right button panels to the main button panel
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(middleButtonPanel, BorderLayout.CENTER); // Place the new button in the middle
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);

        // Add components to the frame
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Helper method to perform the search
    private void performSearch(String s,TableRowSorter<DefaultTableModel> sorterVar) {
        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + s); // Case-insensitive search
        sorterVar.setRowFilter(rowFilter);
    }

    private void openChatRoom(String username) {
        // Create and display the chat room page with the username
        ChatRoomPage chatRoomPage = new ChatRoomPage(username);
        chatRoomPage.setVisible(true);
    }
    
    private List<String> readBranchStats(String branchName) {
        List<String> branchBoughtItemsHistory = new ArrayList<>();
        String fileName = "AllBoughtItems.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(branchName)) {
                	branchBoughtItemsHistory.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return branchBoughtItemsHistory;
    }


    private void showStoreStatistics() {
        // Read the purchase history for this branch from a file
        List<String> branchItemsBought = readBranchStats("Holon"); // Change "Holon" to the desired branch name

        // Create a custom dialog to display the purchase history in a sortable table
        JDialog historyDialog = new JDialog(this, "Holon's Purchase History", true);
        historyDialog.setLayout(new BorderLayout());

        String[] columnNames = {"Item", "Description", "Size", "Quantity", "Price For Each", "Branch"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        
        JTable historyTable = new JTable(model);

        // Enable sorting for the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        historyTable.setRowSorter(sorter);

        // Add the purchase history to the table
        for (String purchase : branchItemsBought) {
            String[] parts = purchase.split(":");
            String item = parts[0];
            String description = parts[1];
            String size = parts[2];
            int quantity = Integer.parseInt(parts[3]);
            double price = Double.parseDouble(parts[4].substring(1)); // Remove "$" and parse
            String branch = parts[5];

            model.addRow(new Object[]{item, description, size, quantity, "$" + price, branch});
        }

        JScrollPane scrollPane = new JScrollPane(historyTable);
        historyDialog.add(scrollPane, BorderLayout.CENTER);

        // Create a search field and add a DocumentListener to filter the table dynamically
        JTextField searchField2 = new JTextField(20);
        searchField2.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				performSearch(searchField2.getText(),sorter);

			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				performSearch(searchField2.getText(),sorter);

			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				performSearch(searchField2.getText(),sorter);

			}
		});

        JPanel searchPanel2 = new JPanel();
        searchPanel2.add(new JLabel("Search:"));
        searchPanel2.add(searchField2);

        // Add the search field and close button to the dialog
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyDialog.dispose(); // Close the dialog
            }
        });
        buttonPanel.add(closeButton);

        historyDialog.add(searchPanel2, BorderLayout.NORTH);
        historyDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Set dialog size and make it visible
        historyDialog.setSize(600, 400);
        historyDialog.setVisible(true);

        // Method to filter the table based on the search text
       
    }



    public static void main(String[] args) {
        // Example usage: Create a HolonPage frame with a username
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HolonPage holonPage = new HolonPage("JohnDoe");
                holonPage.setVisible(true);
            }
        });
    }
}
