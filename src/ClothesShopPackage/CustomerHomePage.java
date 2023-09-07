package ClothesShopPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class CustomerHomePage extends JFrame {
    private String username;

    public CustomerHomePage(String username) {
        this.username = username;

        setTitle("Customer Home Page");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement code to view customer profile
                JOptionPane.showMessageDialog(CustomerHomePage.this, "Viewing Customer Profile");
            }
        });

        JButton viewAllProductsButton = new JButton("View all available products");
        viewAllProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAllProductsCustomersPage();
            }
        });

        JButton viewPurchaseHistoryButton = new JButton("View Purchase History");
        viewPurchaseHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPurchaseHistory();
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement code to logout the customer
                logout();
            }
        });

        buttonPanel.add(viewProfileButton);
        buttonPanel.add(viewAllProductsButton);
        buttonPanel.add(viewPurchaseHistoryButton); // Add the new button
        buttonPanel.add(logoutButton);

        add(welcomeLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void openAllProductsCustomersPage() {
        // Open the All Products Customers Page
        AllProductsCustomerPage allProductsCustomersPage = new AllProductsCustomerPage(username, this);
    }

    private void viewPurchaseHistory() {
        // Read the purchase history for this customer from a file
        List<String> purchaseHistory = readPurchaseHistory(username);

        // Create a custom dialog to display the purchase history in a table
        JDialog historyDialog = new JDialog(this, "Purchase History", true);
        historyDialog.setLayout(new BorderLayout());

        String[] columnNames = {"Item", "Description", "Size", "Quantity", "Price For Each", "Branch"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable historyTable = new JTable(model);

        // Add the purchase history to the table
        for (String purchase : purchaseHistory) {
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

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyDialog.dispose(); // Close the dialog
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        historyDialog.add(buttonPanel, BorderLayout.SOUTH);

        historyDialog.setSize(800, 400);
        historyDialog.setLocationRelativeTo(this);
        historyDialog.setVisible(true);
    }




    private List<String> readPurchaseHistory(String customerName) {
        List<String> purchaseHistory = new ArrayList<>();
        String fileName = "AllBoughtItems.txt"; // Change this to the actual file name

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(customerName)) {
                    purchaseHistory.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return purchaseHistory;
    }

    private void logout() {
        // Implement code to logout the customer
        JOptionPane.showMessageDialog(this, "Logging out");
        dispose(); // Close the customer home page
        // You can return to the login page or perform other logout actions as needed
    }

    public void refreshProductsPage() {
        // Implement code to refresh the products page here
        // You may need to update the data or perform any necessary actions
    }

    public static void main(String[] args) {
        // Example usage
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CustomerHomePage customerHomePage = new CustomerHomePage("TESTER CUSTOMER");
                customerHomePage.setVisible(true);
            }
        });
    }
}