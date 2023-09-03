package ClothesShopPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EmployeeManagementPage extends JFrame {
    private EmployeeManager employeeManager;
    private JTextField fullNameField;
    private JTextField postalCodeField;
    private JTextField phoneNumberField;
    private JTextField accountNumberField;
    private JTextField branchAffiliationField;
    private JTextField employeeNumberField;
    private JTextField positionField;
    private JTextField passwordField;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public EmployeeManagementPage(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;

        setTitle("Employee Management");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Initialize and populate the table with employee data
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Full Name");
        tableModel.addColumn("Postal Code");
        tableModel.addColumn("Phone Number");
        tableModel.addColumn("Account Number");
        tableModel.addColumn("Branch Affiliation");
        tableModel.addColumn("Employee Number");
        tableModel.addColumn("Position");
        tableModel.addColumn("Password");
        employeeTable = new JTable(tableModel);
        populateEmployeeTable();

        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // Create input fields and buttons
        fullNameField = new JTextField(20);
        postalCodeField = new JTextField(10);
        phoneNumberField = new JTextField(10);
        accountNumberField = new JTextField(15);
        branchAffiliationField = new JTextField(15);
        employeeNumberField = new JTextField(10);
        positionField = new JTextField(10);
        passwordField = new JTextField(10);

        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a dialog or form to enter employee information
                openAddEmployeeDialog();
            }
        });
        
        JButton updateButton = new JButton("Update Employee");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
            }
        });

        JButton deleteButton = new JButton("Delete Employee");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        // Create a panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(new JLabel("Full Name:"));
        inputPanel.add(fullNameField);
        // Add more input fields here for other employee attributes

        inputPanel.add(addEmployeeButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);

        // Add components to the frame
        add(inputPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Implement methods for adding, updating, and deleting employees
    private void addEmployee(Employee employee) {
        // Add the Employee to the EmployeeManager
   
       
    }


    private void updateEmployee() {
        // Get data from input fields and update the selected Employee
        // Update the table with the updated data
    }

    private void deleteEmployee() {
        // Delete the selected Employee from the employeeManager
        // Remove the Employee from the table
    }

    private void populateEmployeeTable() {
        // Clear existing data in the table
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }

        // Load employee data from the employee DB text file and add it to the table
        try (BufferedReader reader = new BufferedReader(new FileReader("employeeDB.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" : ");
                if (parts.length == 8) { // Ensure that the line has all employee attributes
                    Object[] row = { parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7] };
                    tableModel.addRow(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private void openAddEmployeeDialog() {
        // Create a dialog to input employee information
        JDialog addEmployeeDialog = new JDialog(this, "Add Employee", true);
        addEmployeeDialog.setSize(300, 250);
        addEmployeeDialog.setLayout(new GridLayout(7, 2));

        // Add input fields for employee information (Full Name, Postal Code, etc.)
        JTextField fullNameField = new JTextField();
        JTextField postalCodeField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        JTextField accountNumberField = new JTextField();
        JTextField branchAffiliationField = new JTextField();
        JTextField employeeNumberField = new JTextField();
        JTextField employeePasswordField = new JTextField();
        
        // Create a combo box for selecting the employee role
        String[] roles = {"Shift Manager", "Cashier", "Seller"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);

        JButton addButton = new JButton("Add");

        addEmployeeDialog.add(new JLabel("Full Name:"));
        addEmployeeDialog.add(fullNameField);
        addEmployeeDialog.add(new JLabel("Postal Code:"));
        addEmployeeDialog.add(postalCodeField);
        addEmployeeDialog.add(new JLabel("Phone Number:"));
        addEmployeeDialog.add(phoneNumberField);
        addEmployeeDialog.add(new JLabel("Account Number:"));
        addEmployeeDialog.add(accountNumberField);
        addEmployeeDialog.add(new JLabel("Branch Affiliation:"));
        addEmployeeDialog.add(branchAffiliationField);
        addEmployeeDialog.add(new JLabel("Employee Number:"));
        addEmployeeDialog.add(employeeNumberField);
        addEmployeeDialog.add(new JLabel("Employee Role:"));
        addEmployeeDialog.add(roleComboBox);
        addEmployeeDialog.add(new JLabel("Password:"));
        addEmployeeDialog.add(employeePasswordField);
        addEmployeeDialog.add(new JLabel()); // Empty label for spacing
        addEmployeeDialog.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the entered employee information
                String fullName = fullNameField.getText();
                String postalCode = postalCodeField.getText();
                String phoneNumber = phoneNumberField.getText();
                String accountNumber = accountNumberField.getText();
                String branchAffiliation = branchAffiliationField.getText();
                String employeeNumber = employeeNumberField.getText();
                String selectedRole = (String) roleComboBox.getSelectedItem();
                String password = employeePasswordField.getText();
                // Create an Employee object with the entered information
                Employee newEmployee = new Employee(fullName, postalCode, phoneNumber, accountNumber, branchAffiliation, employeeNumber, selectedRole, password);

                // Add the employee to the EmployeeManager
                EmployeeManager.addEmployee(newEmployee);

                // Add the employee to the user database
                String username = fullName.replace(" ", ""); // Generate a username from the full name (remove spaces)
                String passwd= password; // Set a default password for the new employee
                Authentication.addUser(username, passwd, branchAffiliation);
                writeEmployeeToDB(newEmployee);
                updateEmployeeTable();
                // Close the dialog
                addEmployeeDialog.dispose();
            }
        });

        addEmployeeDialog.setVisible(true);
    }
 // Helper method to write an employee to the employeeDB.txt file
    private void writeEmployeeToDB(Employee employee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employeeDB.txt", true))) {
            // Format the employee's information as a line in the file
            String employeeInfo = employee.getFullName() + " : " +
                                  employee.getPostalCode() + " : " +
                                  employee.getPhoneNumber() + " : " +
                                  employee.getAccountNumber() + " : " +
                                  employee.getBranchAffiliation() + " : " +
                                  employee.getEmployeeNumber() + " : " +
                                  employee.getPosition() + " : " +
                                  employee.getPassword();

            // Write the employee's information to the file
            writer.write(employeeInfo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error writing to employeeDB.txt.");
        }
    }
   
    private void updateEmployeeTable() {
        // Clear existing data in the table
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }

        // Populate the table with updated employee data
        populateEmployeeTable();
    }


}
