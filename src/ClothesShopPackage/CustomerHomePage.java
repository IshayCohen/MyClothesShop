package ClothesShopPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement code to logout the customer
                logout();
            }
        });

        buttonPanel.add(viewProfileButton);
        buttonPanel.add(logoutButton);

        add(welcomeLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void logout() {
        // Implement code to log out the customer
        JOptionPane.showMessageDialog(this, "Logged out successfully.");
        dispose(); // Close the customer home page
        // You may want to navigate back to the login screen or perform other actions here.
    }

}
