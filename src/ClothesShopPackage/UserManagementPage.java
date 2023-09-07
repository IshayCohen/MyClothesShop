package ClothesShopPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManagementPage extends JFrame {
    private DefaultTableModel customerTableModel;
    private DefaultTableModel employeeTableModel;

    public UserManagementPage() {
        setTitle("User Management Page");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Create table models for customers and employees
        customerTableModel = new DefaultTableModel();
        employeeTableModel = new DefaultTableModel();

        // Create customer and employee tables
        JTable customerTable = new JTable(customerTableModel);
        JTable employeeTable = new JTable(employeeTableModel);

        // Add columns to customer table
        customerTableModel.addColumn("Username");
        customerTableModel.addColumn("Name");
        customerTableModel.addColumn("ID");
        customerTableModel.addColumn("Phone");

        // Add columns to employee table
        employeeTableModel.addColumn("Username");
        employeeTableModel.addColumn("Full Name");
        employeeTableModel.addColumn("Postal Code");
        employeeTableModel.addColumn("Phone");
        employeeTableModel.addColumn("Account Number");
        employeeTableModel.addColumn("Branch Affiliation");
        employeeTableModel.addColumn("Position");

        // Load customer data from customer.txt
        loadUsersFromTextFile("customer.txt", customerTableModel);

        // Load employee data from employee.txt
        loadUsersFromTextFile("employee.txt", employeeTableModel);

        // Create a tabbed pane to display customer and employee tables
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Customers", new JScrollPane(customerTable));
        tabbedPane.addTab("Employees", new JScrollPane(employeeTable));

        // Add the tabbed pane to the frame
        add(tabbedPane);
    }

    private void loadUsersFromTextFile(String fileName, DefaultTableModel tableModel) {
        // Read user data from the specified file and add them to the table model
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");
                if (userData.length > 0) {
                    tableModel.addRow(userData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}