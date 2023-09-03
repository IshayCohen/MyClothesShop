package ClothesShopPackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CustomerRegistrationPage extends JFrame {
    private JTextField nameField;
    private JTextField idField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JComboBox<String> typeComboBox;

    public CustomerRegistrationPage() {
        setTitle("Customer Registration");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(20);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        JLabel typeLabel = new JLabel("Type:");
        String[] customerTypes = {"New", "Return", "VIP"};
        typeComboBox = new JComboBox<>(customerTypes);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve user input
                String name = nameField.getText();
                String id = idField.getText();
                String phone = phoneField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                String type = (String) typeComboBox.getSelectedItem();

                // Register the customer
                registerCustomer(name, id, phone, password, type);

                // Clear the input fields after registration
                nameField.setText("");
                idField.setText("");
                phoneField.setText("");
                passwordField.setText("");
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(typeLabel);
        panel.add(typeComboBox);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(registerButton);

        add(panel);
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void registerCustomer(String name, String id, String phone, String password, String type) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("customerDB.txt", true))) {
            // Format the customer's information as a line in the file
            String customerInfo = name + " : " + id + " : " + phone + " : " + password + " : " + type;

            // Write the customer's information to the file
            writer.write(customerInfo);
            writer.newLine();

            // Display a confirmation message
            JOptionPane.showMessageDialog(null, "Customer registered:\nName: " + name + "\nID: " + id + "\nPhone: " + phone + "\nType: " + type);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error writing to customerDB.txt.");
        }
    }

}
