package ClothesShopPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterPage extends JFrame {
    private JTextField fullNameField;
    private JTextField postalCodeField;
    private JTextField phoneNumberField;
    private JTextField accountNumberField;
    private JComboBox<String> branchComboBox;
    private JTextField employeeNumberField;
    private JComboBox<String> roleComboBox;
    private JPasswordField passwordField;
    private JButton registerButton;

    public RegisterPage() {
        setTitle("Register New Employee");
        setSize(400, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(9, 2));

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameField = new JTextField();
        
        JLabel postalCodeLabel = new JLabel("Postal Code:");
        postalCodeField = new JTextField();
        
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField();
        
        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberField = new JTextField();

        JLabel branchLabel = new JLabel("Branch Affiliation:");
        String[] branchOptions = {"Holon", "Tel Aviv", "Rishon LeTzion"};
        branchComboBox = new JComboBox<>(branchOptions);

        JLabel employeeNumberLabel = new JLabel("Employee Number:");
        employeeNumberField = new JTextField();

        JLabel roleLabel = new JLabel("Employee Role:");
        String[] roleOptions = {"Shift Manager", "Cashier", "Seller"};
        roleComboBox = new JComboBox<>(roleOptions);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        registerButton = new JButton("Register");

        add(fullNameLabel);
        add(fullNameField);
        add(postalCodeLabel);
        add(postalCodeField);
        add(phoneNumberLabel);
        add(phoneNumberField);
        add(accountNumberLabel);
        add(accountNumberField);
        add(branchLabel);
        add(branchComboBox);
        add(employeeNumberLabel);
        add(employeeNumberField);
        add(roleLabel);
        add(roleComboBox);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel()); // Empty label for spacing
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = fullNameField.getText();
                String postalCode = postalCodeField.getText();
                String phoneNumber = phoneNumberField.getText();
                String accountNumber = accountNumberField.getText();
                String branchAffiliation = (String) branchComboBox.getSelectedItem();
                String employeeNumber = employeeNumberField.getText();
                String role = (String) roleComboBox.getSelectedItem();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // Create the registration entry
                String registrationEntry = fullName + " : " + postalCode + " : " + phoneNumber +
                        " : " + accountNumber + " : " + branchAffiliation + " : " + employeeNumber +
                        " : " + role + " : " + password;

                // Write the registration entry to the "employeeDB.txt" file
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("employeeDB.txt", true));
                    writer.write(registrationEntry);
                    writer.newLine();
                    writer.close();

                    JOptionPane.showMessageDialog(RegisterPage.this, "Employee registered successfully!");

                    dispose(); // Close the registration page after registration
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(RegisterPage.this, "Error writing to file.");
                }
            }
        });
    }
}
