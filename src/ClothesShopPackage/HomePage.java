package ClothesShopPackage;

import javax.swing.*;
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
        setLayout(new GridLayout(4, 1));

        JButton telAvivButton = new JButton("Tel Aviv");
        JButton rishonButton = new JButton("Rishon LeTzion");
        JButton holonButton = new JButton("Holon");
        JButton logoutButton = new JButton("Logout");

        add(telAvivButton);
        add(rishonButton);
        add(holonButton);
        add(logoutButton);
        
        JButton userManagementButton = new JButton("User Management Page");
        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserManagementPage();
            }
        });
        
        
        add(userManagementButton);
        
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
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
            }
        });
    }

    private void openBranchPage(String branch) {
        // Code to open the branch page based on the selected branch
        // You can implement this logic similar to your existing branch pages
    }
    
    private void openUserManagementPage() {
        // Open the User Management Page
        UserManagementPage userManagementPage = new UserManagementPage();
        userManagementPage.setVisible(true);
    }
    
}
