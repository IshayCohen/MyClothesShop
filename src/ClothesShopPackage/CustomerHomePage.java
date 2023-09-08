package ClothesShopPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import ClothesShop.StoreClient;

public class CustomerHomePage extends JFrame {
	 	private String username;
	    private JTextField passwordField;
	    private JTextField nameField;
	    private JTextField idField;
	    private JTextField phoneField;

    public CustomerHomePage(String username) {
        this.username = username;

        setTitle("Customer Home Page");
        setSize(400, 300);
        setLayout(new BorderLayout());
        

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
       // JPanel buttonPanel = new JPanel(new GridLayout(4, 1));


        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement code to view customer profile
            	viewProfile();
      
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
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logoutButton);
        add(logoutPanel, BorderLayout.SOUTH);
    }
    private void viewProfile() {
        // Create a custom dialog to display and edit the customer profile
        JDialog profileDialog = new JDialog(this, "Customer Profile", true);
        profileDialog.setLayout(new BorderLayout());

        // Read customer information from the "customer.txt" file
        String[] customerInfo = readCustomerInfo(username);

        // Create labels and text fields for customer information
        JPanel infoPanel = new JPanel(new GridLayout(5, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel idLabel = new JLabel("ID:");
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField usernameField = new JTextField(username);
        passwordField = new JTextField(customerInfo[1]);
        nameField = new JTextField(customerInfo[2]);
        idField = new JTextField(customerInfo[3]);
        phoneField = new JTextField(customerInfo[4]);

        // Make the username field non-editable
        usernameField.setEditable(false);

        infoPanel.add(usernameLabel);
        infoPanel.add(usernameField);
        infoPanel.add(passwordLabel);
        infoPanel.add(passwordField);
        infoPanel.add(nameLabel);
        infoPanel.add(nameField);
        infoPanel.add(idLabel);
        infoPanel.add(idField);
        infoPanel.add(phoneLabel);
        infoPanel.add(phoneField);

        // Create Save and Cancel buttons
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProfile(username);
                profileDialog.dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profileDialog.dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add components to the dialog
        profileDialog.add(infoPanel, BorderLayout.CENTER);
        profileDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Set dialog size and make it visible
        profileDialog.setSize(400, 200);
        profileDialog.setLocationRelativeTo(this);
        profileDialog.setVisible(true);
    }

    private String[] readCustomerInfo(String customerUsername) {
        String fileName = "customer.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String username = parts[0];
                if (username.equals(customerUsername)) {
                    // Match found, return the customer information
                    return parts;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveProfile(String customerUsername) {
        String fileName = "customer.txt";
        String[] lines = new String[5]; // To store updated lines

        // Read the existing lines from the customer.txt file and populate the array
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null && index < 5) {
                lines[index] = line;
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit without saving if an error occurs
        }

        // Update the customer information in the array
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split(";");
            String username = parts[0];
            if (username.equals(customerUsername)) {
                // Update the customer information with the edited values
                parts[1] = passwordField.getText();
                parts[2] = nameField.getText();
                parts[3] = idField.getText();
                parts[4] = phoneField.getText();
                lines[i] = String.join(";", parts);
                break; // Exit the loop after updating
            }
        }

        // Write the updated lines back to the customer.txt file
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String line : lines) {
                if (line != null) {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Profile updated successfully.");
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
        String fileName = "AllBoughtItems.txt";

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
        StoreClient.start();
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