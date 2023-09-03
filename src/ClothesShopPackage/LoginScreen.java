package ClothesShopPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton, customerRegisterButton;
    private JRadioButton employeeRadioButton;
    private JRadioButton customerRadioButton;
    
    public LoginScreen() {
        setTitle("Clothes Shop Login");
        setSize(300, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 3));
        
        employeeRadioButton = new JRadioButton("Employee");
        customerRadioButton = new JRadioButton("Customer");
        customerRadioButton.setSelected(true);
        ButtonGroup group = new ButtonGroup();
       

        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        customerRegisterButton = new JButton("Customer Register");
        group.add(employeeRadioButton);
        group.add(customerRadioButton);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel()); // Empty label for spacing
        add(loginButton);
        add(new JLabel()); // Empty label for spacing
        add(registerButton);
        add(new JLabel()); // Empty label for spacing
        add(customerRegisterButton);
        add(employeeRadioButton);
        add(customerRadioButton);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (customerRadioButton.isSelected()) {
                    // Check customer login
                    if (CustomerAuthentication.isValidCustomer(username, password)) {
                        // Customer authentication successful
                        CustomerHomePage customerHomePage = new CustomerHomePage(username);
                        customerHomePage.setVisible(true);
                        dispose(); // Close the login screen
                    } else {
                        JOptionPane.showMessageDialog(LoginScreen.this, "Invalid customer username or password. Please try again.");
                    }
                } else if (employeeRadioButton.isSelected()) {
                    // Check employee login
                    if (EmployeeAuthentication.isValidEmployee(username, password)) {
                        if (username.equals("admin") && password.equals("admin")) {
                            HomePage homePage = new HomePage(username);
                            homePage.setVisible(true);
                        } else {
                            String branch = EmployeeAuthentication.getBranch(username);
                            if (branch != null) {
                                if (branch.equals("Holon")) {
                                    HolonPage holonPage = new HolonPage(username);
                                    holonPage.setVisible(true);
                                } else if (branch.equals("Rishon LeTzion")) {
                                    RishonLeTzionPage rishonPage = new RishonLeTzionPage(username);
                                    rishonPage.setVisible(true);
                                } else if (branch.equals("Tel Aviv")) {
                                    TelAvivPage telAvivPage = new TelAvivPage(username);
                                    telAvivPage.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(LoginScreen.this, "Invalid user branch.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(LoginScreen.this, "User branch not found.");
                            }
                        }
                        dispose(); // Close the login screen
                    } else {
                        JOptionPane.showMessageDialog(LoginScreen.this, "Invalid employee username or password. Please try again.");
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginScreen.this, "Please select either Employee or Customer.");
                }
            }
        });


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterPage registerPage = new RegisterPage();
                registerPage.setVisible(true);
            }
        });
        
        customerRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerRegistrationPage customerRegisterPage = new CustomerRegistrationPage();
                customerRegisterPage.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginScreen().setVisible(true);
            }
        });
    }
}
