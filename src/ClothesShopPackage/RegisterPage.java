package ClothesShopPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterPage extends JFrame {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JComboBox<String> branchComboBox;
    private JButton registerButton;

    public RegisterPage() {
        setTitle("Register New User");
        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel branchLabel = new JLabel("Branch:");
        String[] branchOptions = {"Holon", "Tel Aviv", "Rishon LeTzion"};
        branchComboBox = new JComboBox<>(branchOptions);
        registerButton = new JButton("Register");

        add(nameLabel);
        add(nameField);
        add(passwordLabel);
        add(passwordField);
        add(branchLabel);
        add(branchComboBox);
        add(new JLabel()); // Empty label for spacing
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                String selectedBranch = (String) branchComboBox.getSelectedItem();

                // Create the registration entry
                String registrationEntry = name + " : " + password + " : " + selectedBranch;

                // Write the registration entry to the "users" text file
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
                    writer.write(registrationEntry);
                    writer.newLine();
                    writer.close();

                    JOptionPane.showMessageDialog(RegisterPage.this, "User registered successfully!");

                    dispose(); // Close the registration page after registration
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(RegisterPage.this, "Error writing to file.");
                }
            }
        });
    }
}
