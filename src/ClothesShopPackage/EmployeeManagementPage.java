package ClothesShopPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        //setDefaultCloseOperation(EXIT_ON_CLOSE);

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
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to update.");
            return;
        }

        // Get the selected employee's data from the table
        String fullName = (String) tableModel.getValueAt(selectedRow, 0);
        String postalCode = (String) tableModel.getValueAt(selectedRow, 1);
        String phoneNumber = (String) tableModel.getValueAt(selectedRow, 2);
        String accountNumber = (String) tableModel.getValueAt(selectedRow, 3);
        String branchAffiliation = (String) tableModel.getValueAt(selectedRow, 4);
        String employeeNumber = (String) tableModel.getValueAt(selectedRow, 5);
        String position = (String) tableModel.getValueAt(selectedRow, 6);
        String password = (String) tableModel.getValueAt(selectedRow, 7);

        // Create a dialog for updating the selected employee's data
        JDialog updateEmployeeDialog = new JDialog(this, "Update Employee", true);
        updateEmployeeDialog.setSize(300, 250);
        updateEmployeeDialog.setLayout(new GridLayout(8, 2));

        JTextField updatedFullNameField = new JTextField(fullName);
        JTextField updatedPostalCodeField = new JTextField(postalCode);
        JTextField updatedPhoneNumberField = new JTextField(phoneNumber);
        JTextField updatedAccountNumberField = new JTextField(accountNumber);
        JTextField updatedBranchAffiliationField = new JTextField(branchAffiliation);
        JTextField updatedEmployeeNumberField = new JTextField(employeeNumber);
        JTextField updatedPositionField = new JTextField(position);
        JTextField updatedPasswordField = new JTextField(password);

        JButton applyButton = new JButton("Apply");

        updateEmployeeDialog.add(new JLabel("Full Name:"));
        updateEmployeeDialog.add(updatedFullNameField);
        updateEmployeeDialog.add(new JLabel("Postal Code:"));
        updateEmployeeDialog.add(updatedPostalCodeField);
        updateEmployeeDialog.add(new JLabel("Phone Number:"));
        updateEmployeeDialog.add(updatedPhoneNumberField);
        updateEmployeeDialog.add(new JLabel("Account Number:"));
        updateEmployeeDialog.add(updatedAccountNumberField);
        updateEmployeeDialog.add(new JLabel("Branch Affiliation:"));
        updateEmployeeDialog.add(updatedBranchAffiliationField);
        updateEmployeeDialog.add(new JLabel("Employee Number:"));
        updateEmployeeDialog.add(updatedEmployeeNumberField);
        updateEmployeeDialog.add(new JLabel("Position:"));
        updateEmployeeDialog.add(updatedPositionField);
        updateEmployeeDialog.add(new JLabel("Password:"));
        updateEmployeeDialog.add(updatedPasswordField);
        updateEmployeeDialog.add(new JLabel()); // Empty label for spacing
        updateEmployeeDialog.add(applyButton);

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the updated data from the dialog
                String updatedFullName = updatedFullNameField.getText();
                String updatedPostalCode = updatedPostalCodeField.getText();
                String updatedPhoneNumber = updatedPhoneNumberField.getText();
                String updatedAccountNumber = updatedAccountNumberField.getText();
                String updatedBranchAffiliation = updatedBranchAffiliationField.getText();
                String updatedEmployeeNumber = updatedEmployeeNumberField.getText();
                String updatedPosition = updatedPositionField.getText();
                String updatedPassword = updatedPasswordField.getText();

                // Create an updated Employee object with the new data
                Employee updatedEmployee = new Employee(updatedFullName, updatedPostalCode, updatedPhoneNumber,
                        updatedAccountNumber, updatedBranchAffiliation, updatedEmployeeNumber, updatedPosition,
                        updatedPassword);

                // Update the Employee in the EmployeeManager (if needed)
                // employeeManager.updateEmployee(updatedEmployee);

                // Remove the old employee record from the employeeDB.txt file
                removeEmployeeFromDB(employeeNumber);

                // Write the updated employee data to the employeeDB.txt file
                writeEmployeeToDB(updatedEmployee);

                // Refresh the table to reflect the changes
                updateEmployeeTable();

                // Close the dialog
                updateEmployeeDialog.dispose();

                JOptionPane.showMessageDialog(EmployeeManagementPage.this, "Employee updated successfully.");
            }
        });

        updateEmployeeDialog.setVisible(true);
    }

    private void removeEmployeeFromDB(String employeeNumberToRemove) {
        try {
            // Read all lines from the employeeDB.txt file
            List<String> lines = Files.readAllLines(Paths.get("employeeDB.txt"));

            // Create a new list to store the updated lines (excluding the old employee record)
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(" : ");
                if (parts.length == 8 && !parts[5].equals(employeeNumberToRemove)) {
                    // Only add lines that are not the old employee record
                    updatedLines.add(line);
                }
            }

            // Write the updated lines back to the employeeDB.txt file
            Files.write(Paths.get("employeeDB.txt"), updatedLines, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error removing old employee record from employeeDB.txt.");
        }
    }

    

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();

        if (selectedRow == -1) {
            // No employee selected, show an error message
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Get the employee information from the selected row
            String fullName = (String) tableModel.getValueAt(selectedRow, 0);
            String postalCode = (String) tableModel.getValueAt(selectedRow, 1);
            String phoneNumber = (String) tableModel.getValueAt(selectedRow, 2);
            String accountNumber = (String) tableModel.getValueAt(selectedRow, 3);
            String branchAffiliation = (String) tableModel.getValueAt(selectedRow, 4);
            String employeeNumber = (String) tableModel.getValueAt(selectedRow, 5);
            String position = (String) tableModel.getValueAt(selectedRow, 6);
            String password = (String) tableModel.getValueAt(selectedRow, 7);

            // Create an Employee object from the selected employee's information
            Employee selectedEmployee = new Employee(fullName, postalCode, phoneNumber, accountNumber, branchAffiliation, employeeNumber, position, password);

            // Remove the selected employee from the employeeManager
            //employeeManager.removeEmployee(selectedEmployee);

            // Remove the selected employee from the employeeDB.txt file
            removeFromEmployeeDB(selectedEmployee);

            // Remove the selected employee from the table
            tableModel.removeRow(selectedRow);
        }
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
    
 
 // Helper method to remove an employee from the employeeDB.txt file
    private void removeFromEmployeeDB(Employee employee) {
        try {
            // Read all lines from the employeeDB.txt file
            List<String> lines = Files.readAllLines(Paths.get("employeeDB.txt"));

            // Create a new list of lines without the selected employee's information
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                String[] parts = line.split(" : ");
                if (parts.length == 8) {
                    // Check if the employee number in the line matches the selected employee's number
                    if (!parts[5].equals(employee.getEmployeeNumber())) {
                        updatedLines.add(line);
                    }
                }
            }

            // Write the updated lines back to the employeeDB.txt file
            Files.write(Paths.get("employeeDB.txt"), updatedLines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error removing employee from employeeDB.txt.");
        }
    }




}
