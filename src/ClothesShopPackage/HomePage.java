package ClothesShopPackage;

import javax.swing.*;

import ClothesShop.StoreClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private String username;

    public HomePage(String username) {
        this.username = username;
        setTitle("Home Page - Welcome, " + username + "!");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create a main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a panel for buttons with GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));

        JButton telAvivButton = new JButton("Tel Aviv");
        JButton rishonButton = new JButton("Rishon LeTzion");
        JButton holonButton = new JButton("Holon");

        // Create buttons for additional actions
        JButton userManagementButton = new JButton("User Management Page");
        JButton seeAllProductsButton = new JButton("See All Products");
        JButton logoutButton = new JButton("Logout");

        // Add buttons to the button panel
        buttonPanel.add(telAvivButton);
        buttonPanel.add(rishonButton);
        buttonPanel.add(holonButton);
        buttonPanel.add(logoutButton);

        // Create a panel for additional actions buttons
        JPanel actionPanel = new JPanel(new GridLayout(2, 1));
        actionPanel.add(userManagementButton);
        actionPanel.add(seeAllProductsButton);

        // Add the button panel and action panel to the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);

        // Position the logout button at the bottom right
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logoutButton);
        add(logoutPanel, BorderLayout.SOUTH);

        telAvivButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBranchPage("Tel Aviv");
            }
        });

        rishonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBranchPage("Rishon LeTzion");
            }
        });

        holonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBranchPage("Holon");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform logout action here
                dispose(); // Close the home page
                StoreClient.start();
            }
        });

        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserManagementPage();
            }
        });

        seeAllProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAllProductsPage();
            }
        });

        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void openBranchPage(String branch) {
        if (branch.equals("Tel Aviv")) {
            TelAvivPage telAvivPage = new TelAvivPage(username);
            telAvivPage.setVisible(true);
        } else if (branch.equals("Rishon LeTzion")) {
            RishonLeTzionPage rishonPage = new RishonLeTzionPage(username);
            rishonPage.setVisible(true);
        } else if (branch.equals("Holon")) {
            HolonPage holonPage = new HolonPage(username);
            holonPage.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid branch selected.");
        }
    }


    private void openUserManagementPage() {
        // Open the User Management Page
        UserManagementPage userManagementPage = new UserManagementPage();
        userManagementPage.setVisible(true);
    }

  
    private void openAllProductsPage() {
        // Open the All Products Page
        AllProductsPage allProductsPage = new AllProductsPage();
        allProductsPage.setVisible(true);
    }
}